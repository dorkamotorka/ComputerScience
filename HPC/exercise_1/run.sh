#!/bin/bash

echo "Loading FFmpeg"
module load FFmpeg

echo "Slicing video"
srun --ntasks=1 ffmpeg \
-y -i bbb.mp4 -codec copy -f segment -segment_time 80 \
-segment_list parts.txt part-%d.mp4

echo "Edit video"
sbatch --wait obdelava_sbatch.sh

echo "Combine video"
sed 's/part/file out-part/g' < parts.txt > out-parts.txt 
srun --ntasks=1 ffmpeg -y -f concat -i out-parts.txt -c copy out-bbb.mp4
