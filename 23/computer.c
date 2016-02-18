/* * * * * * * * * * * *
 * computer.c
 *
 * simulates a computer with two registers and six instruction options
 *  
 * reads from instructions in a given input file
 * * * * * * * * * * * */

#include <stdio.h>
#include <stdlib.h>

struct instruction 
{
	short action;
	/* 0 = half, 1 = triple, 2 = increment, 3 = jump, 4 = jump if even,
	 * 5 = jump if odd */
	int reg;
	int jump;
};

void parse_input(char* input_name, struct instruction list[49]);
void start_instructions(struct instruction list[49], int a_start);
void half(struct instruction step, unsigned int regs[2]);
void triple(struct instruction step, unsigned int regs[2]);
void increment(struct instruction step, unsigned int regs[2]);
int jump(struct instruction step);
int jump_even(struct instruction step, unsigned int regs[2]);
int jump_one(struct instruction step, unsigned int regs[2]);

int main(int argc, char* argv[])
{
	struct instruction list[49];

	if (argc != 2)
	{
		printf("Usage: ./computer <input.txt>\n");
		return 1;
	}

	parse_input(argv[1], list);

	start_instructions(list, 0);
	start_instructions(list, 1);

	return 0;
}

void parse_input(char* input_name, struct instruction list[49])
{
	FILE* input = fopen(input_name, "r");
	char line[12];
	int n = 0;

	if (!input)
	{
		printf("Error: could not open file %s\n", input_name);
	}
	
	while (fgets(line, 12, input))
	{
		if (line[0] == 'h')
		{
			list[n].action = 0;
			list[n].reg = (line[4] == 'a') ? 0 : 1;
		}
		else if (line[0] == 't')
		{
			list[n].action = 1;
			list[n].reg = (line[4] == 'a') ? 0 : 1;
		}
		else if (line[0] == 'i')
		{
			list[n].action = 2;
			list[n].reg = (line[4] == 'a') ? 0 : 1;
		}
		else if (line[0] == 'j' && line[2] == 'p')
		{
			list[n].action = 3;
			list[n].jump = atoi(&line[4]);
		}
		else if (line[0] == 'j' && line[2] == 'e')
		{
			list[n].action = 4;
			list[n].reg = (line[4] == 'a') ? 0 : 1;
			list[n].jump = atoi(&line[7]);
		}
		else if (line[0] == 'j' && line[2] == 'o')
		{
			list[n].action = 5;
			list[n].reg = (line[4] == 'a') ? 0 : 1;
			list[n].jump = atoi(&line[7]);
		}
		else
		{
			printf("Error: failed to parse line %s\n", line);
			exit(3);
		}

		n++;
	}

	fclose(input);
}

void start_instructions(struct instruction list[49], int a_start)
{
	unsigned int regs[2] = { 0 };
	regs[0] = a_start;
	int n = 0;
        
	while (n < 50)
	{
		switch (list[n].action)
		{
		case 0 :
			half(list[n], regs);
			n++;
			break;
		case 1:
			triple(list[n], regs);
			n++;
			break;
		case 2:
			increment(list[n], regs);
			n++;
			break;
		case 3:
			n += jump(list[n]);
			break;
		case 4:
			n += jump_even(list[n], regs);
			break;
		case 5:
			n += jump_one(list[n], regs);
			break;
		}	

	}

	printf("a = %u, b = %u when a starts at %d\n", 
	       regs[0], regs[1], a_start);
}

void half(struct instruction step, unsigned int regs[2])
{
	regs[step.reg] = regs[step.reg] / 2;
}
void triple(struct instruction step, unsigned int regs[2])
{
	regs[step.reg] = regs[step.reg] * 3;
}
void increment(struct instruction step, unsigned int regs[2])
{
	regs[step.reg]++;
}
int jump(struct instruction step)
{
	return step.jump;
}
int jump_even(struct instruction step, unsigned int regs[2])
{
	if (regs[step.reg] % 2 == 0)
	{
		return step.jump;
	}
	else
	{
		return 1;
	}
}
int jump_one(struct instruction step, unsigned int regs[2])
{
	if (regs[step.reg] == 1)
	{
		return step.jump;
	}
	else
	{
		return 1;
	}
}
