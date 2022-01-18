- List state of clusters, partitions and nodes

	sinfo

- List all tasks

	squeue

- Search tasks according to user:

	squeue --user=<username>

- Run program X times

	srun --ntasks=X <program>

- Run program on reserved node, add:
	
	--reservation=fri

- Divide task payload between X nodes:

	--nodes=X

- sbatch should be preffered over srun. Specify configuration into a bash file(task.sh): 

	#!/bin/bash
	#SBATCH --job-name=name-of-task
	#SBATCH --partition=gridlong
	#SBATCH --ntasks=4
	#SBATCH --nodes=1
	#SBATCH --mem-per-cpu=100MB
	#SBATCH --output=my_task.out
	#SBATCH --time=00:01:00

	srun hostname

and run it using:

	sbatch ./task.sh

Note that in comparison to srun command, sbatch is non-blocking(does not block until task over).

- Cancel task ran with sbatch:

	scancel <job-id>

- Third way of running tasks is using salloc. First we need to allocate resources that will be used for the task, the we use srun normally. 
This way we don't need to wait for resources when we do srun directly. 
If we allocate a node using salloc, we can ssh to it, but at the same time downside of allocating node for the task is that nobody else can use this node while I am using it.

Allocate X nodes using for a minute: 

	salloc --time=00:01:00 --ntasks=X

ssh to node and execute tasks.

# Interesting facts

All nodes in ceph have root directory synced.
