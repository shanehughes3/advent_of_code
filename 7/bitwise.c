/* * * * * * * * * * * *
 * bitwise.c
 *
 * emulates a circuit of logic gates by performing bitwise operations
 * per a given input file and outputs the signal on a given wire
 * * * * * * * * * * * */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

typedef struct {
	int in1x;
	int in1y;
	int in1set;
	int in2x;
	int in2y;
	int in2set;
	int outx;
	int outy;
} VALUE_PASS;

VALUE_PASS get_values(char* in1, char* in2, char* out);
void line_err(char* line);
int check_set(VALUE_PASS i_o, unsigned short wires[27][27][2]);

int main(int argc, char* argv[])
{
	if (argc != 3)
	{
		printf("Usage: ./bitwise <inputfile.txt> <wire>\n");
		return 1;
	}
	
	FILE* input = fopen(argv[1], "r");
	if (input == NULL)
	{
		printf("Error opening file %s\n", argv[1]);
		return 2;
	}

	if (strlen(argv[2]) > 2)
	{
		printf("Invalid wire query\n");
		return 3;
	}

	char* end_wire = argv[2];

	// wires array represents a two-letter wire name
	// [x][0] represents a single-letter name
	// [0][0] is unused
	// [x][y][0] holds the wire's signal, while 
	// [x][y][1] is 1 if set, else 0
	unsigned short wires[27][27][2];
	memset(&wires, 0, 27 * 27 * 2 * sizeof(unsigned short));

	char line[50];
	char* word;
	char* in1;
	char* in2;
	char* out;
	VALUE_PASS i_o;
	unsigned short in_num;
	int unset = 1;
	
	while (unset != 0)
	{
		unset = 0;
		fseek (input, 0, SEEK_SET);
		while (fgets(line, 50, input))
		{
			word = strtok(line, " ");
			if (!strcmp(word, "NOT"))
			{
				// "NOT a -> c"
				in1 = strtok(NULL, " ");
				word = strtok(NULL, " ");
				out = strtok(NULL, "\n");

				i_o = get_values(in1, NULL, out);
				
				if (!check_set(i_o, wires))
				{
					unset++;
				}
				else
				{
					wires[i_o.outx][i_o.outy][0] = 
					    ~wires[i_o.in1x][i_o.in1y][0];
					wires[i_o.outx][i_o.outy][1] = 1;
				}
			}
			else if (word[0] > 96 && word[0] < 123)
			{
				in1 = word;
				word = strtok(NULL, " ");

				if (!strcmp(word, "AND"))
				{
					// "a AND b -> c"
					in2 = strtok(NULL, " ");
					word = strtok(NULL, " ");
					out = strtok(NULL, "\n");

					i_o = get_values(in1, in2, out);

					if (!check_set(i_o, wires))
					{
						unset++;
					}
					else
					{
					    wires[i_o.outx][i_o.outy][0] = 
					      wires[i_o.in1x][i_o.in1y][0] &
					      wires[i_o.in2x][i_o.in2y][0];
					    wires[i_o.outx][i_o.outy][1] = 1;
					}
				}
				else if (!strcmp(word, "OR"))
				{
					// "a OR b -> c"
					in2 = strtok(NULL, " ");
					word = strtok(NULL, " ");
					out = strtok(NULL, "\n");

					i_o = get_values(in1, in2, out);

					if (!check_set(i_o, wires))
					{
						unset++;
					}
					else
					{
					    wires[i_o.outx][i_o.outy][0] = 
					      wires[i_o.in1x][i_o.in1y][0] |
						wires[i_o.in2x][i_o.in2y][0];
					    wires[i_o.outx][i_o.outy][1] = 1;
					}
				}
				else if (!strcmp(word, "LSHIFT"))
				{
					// "a LSHIFT n -> c"
					in_num = atoi(strtok(NULL, " "));
					word = strtok(NULL, " ");
					out = strtok(NULL, "\n");

					i_o = get_values(in1, NULL, out);

					if (!check_set(i_o, wires))
					{
						unset++;
					}
					else
					{
					    wires[i_o.outx][i_o.outy][0] = 
						wires[i_o.in1x][i_o.in1y][0]
						    << in_num;
					    wires[i_o.outx][i_o.outy][1] = 1;
					}
				}
				else if (!strcmp(word, "RSHIFT"))
				{
					// "a RSHIFT n -> c"
					in_num = atoi(strtok(NULL, " "));
					word = strtok(NULL, " ");
					out = strtok(NULL, "\n");

					i_o = get_values(in1, NULL, out);

					if (!check_set(i_o, wires))
					{
						unset++;
					}
					else
					{
					    wires[i_o.outx][i_o.outy][0] =
					        wires[i_o.in1x][i_o.in1y][0]
						    >> in_num;
					    wires[i_o.outx][i_o.outy][1] = 1;
					}
				}
				else if (!strcmp(word, "->"))
				{
					// "a -> c"
					out = strtok(NULL, "\n");

					i_o = get_values(in1, NULL, out);

					if (!check_set(i_o, wires))
					{
						unset++;
					}
					else
					{
					    wires[i_o.outx][i_o.outy][0] = 
						wires[i_o.in1x][i_o.in1y][0];
					    wires[i_o.outx][i_o.outy][1] = 1;
					}
				}
				else
				{
					line_err(line);
				}
			}
			else if (word[0] > 47 && word[0] < 58)
			{
				in_num = atoi(word);

				in1 = strtok(NULL, " ");
				if (!strcmp(in1, "AND"))
				{
					// "n AND a -> c"
					in2 = strtok(NULL, " ");
					word = strtok(NULL, " ");
					out = strtok(NULL, "\n");

					i_o = get_values(NULL, in2, out);

					if (!check_set(i_o, wires))
					{
						unset++;
					}
					else
					{
					    wires[i_o.outx][i_o.outy][0] = 
						in_num & 
						wires[i_o.in2x][i_o.in2y][0];
					    wires[i_o.outx][i_o.outy][1] = 1;
					}
				}
				else if (!strcmp(in1, "->"))
				{
					// "n -> c"
					out = strtok(NULL, "\n");
				
					i_o = get_values(NULL, NULL, out);

					if (!check_set(i_o, wires))
					{
						unset++;
					}
					else
					{
					    wires[i_o.outx][i_o.outy][0] =
						    in_num;
					    wires[i_o.outx][i_o.outy][1] = 1;
					}
				}
				else
				{
					line_err(line);
				}

			}
			else
			{
				line_err(line);
			}
		}
	}

	i_o = get_values(NULL, NULL, end_wire);


	printf("%s = %d\n", end_wire, wires[i_o.outx][i_o.outy][0]);

	fclose(input);
	return 0;
}


/* converts ascii values of input characters to integers 
 * relative to a = 1, returns a struct containting values
 */
VALUE_PASS get_values(char* in1, char* in2, char* out){
	VALUE_PASS i_o;

	if (in1)
	{
		i_o.in1x = in1[0] - 96;
		i_o.in1y = (in1[1] == '\0') ? 0 : in1[1] - 96;
		i_o.in1set = 1;
	}
	else 
	{
		i_o.in1set = 0;
	}

	if (in2)
	{
		i_o.in2x = in2[0] - 96;
		i_o.in2y = (in2[1] == '\0') ? 0 : in2[1] - 96;
		i_o.in2set = 1;
	}
	else
	{
		i_o.in2set = 0;
	}
	i_o.outx = out[0] - 96;
	i_o.outy = (out[1] == '\0') ? 0 : out[1] - 96;

	return i_o;
}

/* prints error on console along with line causing error
 * (used for initial debugging - this is only semi-functional, as 
 * strtok() sets a null character at the first space - as a result,
 * this function only displays the identifier of the first input wire
 */
void line_err(char* line)
{
	printf("Invalid line: %s", line);
	exit(4);
}

/* checks if the input wires which are set (i.e. will be used)
 * in passed struct have values set in the wires array
 * returns 0 if not, 1 if set
 */
int check_set(VALUE_PASS i_o, unsigned short wires[27][27][2])
{
	if (i_o.in1set && !i_o.in2set)
	{
		if (wires[i_o.in1x][i_o.in1y][1] == 0)
			return 0;
		else 
			return 1;
	}
	else if (!i_o.in1set && i_o.in2set)
	{
		if (wires[i_o.in2x][i_o.in2y][1] == 0)
			return 0;
		else
			return 1;
	}
	else if (i_o.in1set && i_o.in2set)
	{
		if (wires[i_o.in1x][i_o.in1y][1] == 0 ||
		    wires[i_o.in2x][i_o.in2y][1] == 0)
			return 0;
		else
			return 1;
	}
	else
	{
		return 1;
	}
}
