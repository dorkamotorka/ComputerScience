__kernel void calculate_histogram(__global unsigned char *image, 
                    		  __global unsigned int *hist_r, 
                    		  __global unsigned int *hist_g, 
                    		  __global unsigned int *hist_b, 
				  int width,
				  int height,
				  int cpp) 
{                                   
	int i = get_global_id(0);

	if (width*height > i) {
		atomic_add(hist_r + image[i*cpp], 1);
		atomic_add(hist_g + image[i*cpp + 1], 1);
		atomic_add(hist_b + image[i*cpp + 2], 1);
	}
}   
