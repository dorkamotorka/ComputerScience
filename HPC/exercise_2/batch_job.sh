#!/bin/sh
#SBATCH --ntasks=1
#SBATCH --cpus-per-task=1

export OMP_PLACES=cores
export OMP_PROC_BIND=TRUE
export OMP_NUM_THREADS=32

./integral 1
./integral 4
./integral 8
./integral 16
./integral 32
