#include <cstdio>
#include <cmath>
#include <omp.h>
#include <stdio.h>
#include <cstdlib>
#include <iomanip>
#include <iostream>

double f(const double x) {
	return sin(pow(x, 2));
}
struct Result {
  double timestamp, area;
};

const Result trapezoidal_integration(const double a, const double b, int n, const int nThreads) {
	double sum = 0;
	const double dx = (b - a)/n;
	double now = omp_get_wtime();

	#pragma omp parallel for num_threads(nThreads) reduction(+: sum)
	for (int i = 1; i < n; i++) {
		sum += f(a + i * dx);
	} 
	sum = (sum + (f(a) + f(b))/2) * dx;
	return { omp_get_wtime() - now, sum };
}

int main(int argc, char** argv) {
	/*
	const int nThreads[5] = {1, 4, 8, 16, 32};
	std::cout << std::fixed << std::setprecision(8);
	int array_size = sizeof(nThreads)/sizeof(nThreads[0]); 
	for (int i = 0 ; i < array_size ; i++) { 
		Result result = trapezoidal_integration(0, 100, 10000000, nThreads[i]);
		printf("area = %f \n", result.area);
		printf("time = %f \n", result.timestamp);
	}
	*/
	const int nThreads = atoi(argv[1]);;
	std::cout << "Execute integration on " << nThreads << " threads\n";
	Result result = trapezoidal_integration(0, 100, 10000000, nThreads);
	printf("area = %f \n", result.area);
	printf("time = %f \n", result.timestamp);
	return 0;
}
