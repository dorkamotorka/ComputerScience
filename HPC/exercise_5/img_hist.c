#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <omp.h>
#include <CL/cl.h>

#define STB_IMAGE_IMPLEMENTATION
#define STB_IMAGE_WRITE_IMPLEMENTATION
#include "stb_image.h"

#define BINS 256
#define MAX_SOURCE_SIZE	16384

/*
 * ELAPSED TIME CPU: 0.09s
 * ELAPSED TIME GPU: 0.03s
 *
 */

typedef struct _histogram {
    unsigned int *R;
    unsigned int *G;
    unsigned int *B;
} histogram;

void printHistogram(histogram H) {
    printf("Colour\tNo. Pixels\n");
    for (int i = 0; i < BINS; i++) {
        if (H.R[i] > 0)
            printf("%dR\t%d\n", i, H.R[i]);
        if (H.G[i] > 0)
            printf("%dG\t%d\n", i, H.G[i]);
        if (H.B[i] > 0)
            printf("%dB\t%d\n", i, H.B[i]);
    }
}

//=============================== MAIN FUNCTION =======================================//
int main(int argc, char **argv) {
    printf("Starting program");
    char *image_file = argv[1];
    if (argc > 1) {
        image_file = argv[1];
    }
    else {
        fprintf(stderr, "Not enough arguments\n");
        fprintf(stderr, "Usage: %s <IMAGE_PATH>\n", argv[0]);
        exit(1);
    }

    //Initalize the histogram
    histogram H;
    H.B = (unsigned int *)calloc(BINS, sizeof(unsigned int));
    H.G = (unsigned int *)calloc(BINS, sizeof(unsigned int));
    H.R = (unsigned int *)calloc(BINS, sizeof(unsigned int));

    int width, height, cpp;
    unsigned char *image_in = stbi_load(image_file, &width, &height, &cpp, 0);
    if (image_in) {
    	// Get platforms
    	cl_uint num_platforms;
   	cl_int clStatus = clGetPlatformIDs(0, NULL, &num_platforms);
   	cl_platform_id *platforms = (cl_platform_id *)malloc(sizeof(cl_platform_id)*num_platforms);
    	clStatus = clGetPlatformIDs(num_platforms, platforms, NULL);

    	// Get platform devices
    	cl_uint num_devices;
    	clStatus = clGetDeviceIDs(platforms[0], CL_DEVICE_TYPE_GPU, 0, NULL, &num_devices);
	if (clStatus != CL_SUCCESS) {
		printf("Error: Failed to create a device group!\n");
		return EXIT_FAILURE;
	}
	printf("The number of devices found = %d\n", num_devices);

    	num_devices = 1; // limit to one device
    	cl_device_id *devices = (cl_device_id *)malloc(sizeof(cl_device_id)*num_devices);
    	clStatus = clGetDeviceIDs(platforms[0], CL_DEVICE_TYPE_GPU, num_devices, devices, NULL);
	cl_uint buf_uint;
	clGetDeviceInfo(devices[0], 
			CL_DEVICE_MAX_COMPUTE_UNITS, 
			sizeof(buf_uint),
			&buf_uint,
			NULL);
	printf("DEVICE_MAX_COMPUTE_UNITS = %u\n", (unsigned int) buf_uint);

	size_t buf_sizet;
	clGetDeviceInfo(devices[0],
			CL_DEVICE_MAX_WORK_GROUP_SIZE,
			sizeof(buf_sizet),
			&buf_sizet,
			NULL);
	printf("CL_DEVICE_MAX_WORK_GROUP_SIZE = %u\n", (unsigned int)buf_uint);
	clGetDeviceInfo(devices[0],
			CL_DEVICE_MAX_WORK_ITEM_DIMENSIONS,
			sizeof(buf_uint),
			&buf_uint,
			NULL);
	printf("CL_DEVICE_MAX_WORK_ITEM_DIMENSIONS = %u\n", (unsigned int)buf_uint);
	size_t workitem_size[3];
	clGetDeviceInfo(devices[0],
			CL_DEVICE_MAX_WORK_ITEM_SIZES,
			sizeof(workitem_size),
			&workitem_size,
			NULL);
	printf("CL_DEVICE_MAX_WORK_ITEM_SIZES = %u, %u, %u\n",
		(unsigned int)workitem_size [0],
		(unsigned int)workitem_size [1],
		(unsigned int)workitem_size [2]);

    	// Create context for one device
    	cl_context context = clCreateContext(NULL, num_devices, devices, NULL, NULL, &clStatus);
	if (!context) {
		printf("Error: Failed to create a compute context!\n");
		return EXIT_FAILURE;
	}

    	// Create a command queue (0: in order, 1: out of order)
    	cl_command_queue command_queue = clCreateCommandQueue(context, devices[0], 0, &clStatus);
	if (!command_queue) {
		printf("Error: Failed to create a command commands!\n");
		return EXIT_FAILURE;
	}

	// Read and build kernel source
	FILE* fp;
	char* source_str;
	char fileName[100];
	size_t source_size;

	sprintf(fileName, "kernel.cl");
	fp = fopen(fileName, "r");
	if (!fp) {
		fprintf(stderr, ":-(#\n");
		exit(1);
	}
	source_str = (char*)malloc(MAX_SOURCE_SIZE);
	source_size = fread(source_str, 1, MAX_SOURCE_SIZE, fp);
	source_str[source_size] = '\0';
	fclose(fp);

    	// Create and build a program
	printf("Creating kernel source program\n");
    	cl_program program = clCreateProgramWithSource(context, 1, (const char **)&source_str, &source_size, &clStatus);
	if (!program) {
		printf("Error: Failed to create compute program!\n");
		return EXIT_FAILURE;
	}
	free(source_str);

	printf("Building kernel source program\n");
    	clStatus = clBuildProgram(program, 1, devices, NULL, NULL, NULL);
	if (clStatus != CL_SUCCESS) {
		size_t len;
		char buffer [2048];
		printf("Error: Failed to build program executable!\n");
		clGetProgramBuildInfo(program,
					devices[0],
					CL_PROGRAM_BUILD_LOG,
					sizeof(buffer),
					buffer,
					&len);
		printf("%s\n", buffer);
		exit (1) ;
	} else {
		printf("Successfully built kernel source program\n");
	}

	size_t img_size = width * height * cpp * sizeof(unsigned char);
	printf("Image size: %d\n", img_size);
	size_t hist_size = sizeof(unsigned int) * BINS;
	printf("Histogram partial size: %d\n", hist_size);
    	// Create memory buffers on the device
    	cl_mem img_d = clCreateBuffer(context, CL_MEM_READ_WRITE, img_size, NULL, &clStatus);
    	cl_mem hr_d = clCreateBuffer(context, CL_MEM_READ_WRITE, hist_size, NULL, &clStatus);
    	cl_mem hg_d = clCreateBuffer(context, CL_MEM_READ_WRITE, hist_size, NULL, &clStatus);
    	cl_mem hb_d = clCreateBuffer(context, CL_MEM_READ_WRITE, hist_size, NULL, &clStatus);

	// Only measure transfer of the image to the GPU, computation, and transfer of the resulting histograms back to host memory 
        double start = omp_get_wtime();

	// Write host data to device buffers
	clStatus = clEnqueueWriteBuffer(command_queue, hr_d, CL_TRUE, 0, hist_size, H.R, 0, NULL, NULL);
	clStatus = clEnqueueWriteBuffer(command_queue, hg_d, CL_TRUE, 0, hist_size, H.G, 0, NULL, NULL);
	clStatus = clEnqueueWriteBuffer(command_queue, hb_d, CL_TRUE, 0, hist_size, H.B, 0, NULL, NULL);
	clStatus = clEnqueueWriteBuffer(command_queue, img_d, CL_TRUE, 0, img_size, image_in, 0, NULL, NULL);

    	// Create the kernel and set arguments
    	cl_kernel kernel = clCreateKernel(program, "calculate_histogram", &clStatus);
	if (!kernel || clStatus != CL_SUCCESS) {
		printf ("Error: Failed to create compute kernel !\n");
		exit(1);
	}
    	clStatus |= clSetKernelArg(kernel, 0, sizeof(cl_mem), (void *)&img_d);
    	clStatus |= clSetKernelArg(kernel, 1, sizeof(cl_mem), (void *)&hr_d);
    	clStatus |= clSetKernelArg(kernel, 2, sizeof(cl_mem), (void *)&hg_d);
    	clStatus |= clSetKernelArg(kernel, 3, sizeof(cl_mem), (void *)&hb_d);
	clStatus |= clSetKernelArg(kernel, 4, sizeof(cl_int), (void *)&width);
        clStatus |= clSetKernelArg(kernel, 5, sizeof(cl_int), (void *)&height);
    	clStatus |= clSetKernelArg(kernel, 6, sizeof(cl_int), (void *)&cpp);
	if (clStatus != CL_SUCCESS) {
		printf("Error setting parameters to kernel!\n");
	}

	// divide work among threads
	size_t global_worksize = width * height; // size of the problem 
	printf("Global worksize: %d\n", global_worksize);
	size_t local_worksize = 256; // TODO: Vprašaj 
	printf("Local worksize: %d\n", local_worksize);

    	// Execute the kernel
    	clStatus = clEnqueueNDRangeKernel(command_queue, kernel, 1, NULL, &global_worksize, &local_worksize, 0, NULL, NULL);

    	// Read the device memory
    	clStatus = clEnqueueReadBuffer(command_queue, hr_d, CL_TRUE, 0, hist_size, H.R, 0, NULL, NULL);
    	clStatus = clEnqueueReadBuffer(command_queue, hg_d, CL_TRUE, 0, hist_size, H.G, 0, NULL, NULL);
    	clStatus = clEnqueueReadBuffer(command_queue, hb_d, CL_TRUE, 0, hist_size, H.B, 0, NULL, NULL);

        double end = omp_get_wtime();
        printHistogram(H);

    	// Wait for completion and clean-up
    	clStatus = clFlush(command_queue);
    	clStatus = clFinish(command_queue);

    	// Finally release OpenCL objects
    	clStatus = clReleaseKernel(kernel);
    	clStatus = clReleaseProgram(program);
    	clStatus = clReleaseMemObject(img_d);
    	clStatus = clReleaseMemObject(hr_d);
    	clStatus = clReleaseMemObject(hg_d);
    	clStatus = clReleaseMemObject(hb_d);
    	clStatus = clReleaseCommandQueue(command_queue);
    	clStatus = clReleaseContext(context);
    	free(platforms);
    	free(devices);

	double dtg = end - start;
        printf("ElapsedTime: %.2f s\n", dtg);
    } else {
        fprintf(stderr, "Error loading image %s!\n", image_file);
    }

    return 0;
}
