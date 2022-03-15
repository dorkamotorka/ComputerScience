#!/bin/sh
#SBATCH --job-name=obdelava
#SBATCH --output=obdelava-%a.txt         # %a nastavek za imena datotek
#SBATCH --time=00:10:00
#SBATCH --array=0-7                     # območje spreminjanja vrednosti

srun --ntasks=1 ffmpeg \
-y -i part-$SLURM_ARRAY_TASK_ID.mp4 -codec:a copy -filter:v scale=w=3*iw/2:h=3*ih/2 \
out-part-$SLURM_ARRAY_TASK_ID.mp4
