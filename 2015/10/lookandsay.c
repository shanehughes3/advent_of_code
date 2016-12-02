/* * * * * * * * * * *
 * lookandsay.c 
 *
 * does weird things with a sequence of numbers, spits out 
 * the length of another sequence of numbers
 * * * * * * * * * * */

#include <stdio.h>
#include <string.h>
#include <math.h>
#include <stdlib.h>

void step(char* number);

int main(int argc, char* argv[])
{
	char* number = malloc(sizeof(char) * 10000000);
	size_t i;
	
	if (argc != 2)
	{
		printf("Usage: ./lookandsay <inputnumber>\n");
		return 1;
	}
	
	strcpy(number, argv[1]);

	for (i = 0; i < 50; i++)
	{
		step(number);
		printf("iterations: %zu\n", i + 1);
	}
	
	free(number);
//	printf("Length: %zu\n", strlen(number));
	return 0;
}

void step(char* number)
{
	size_t i;
	int j = 0;
	char cpy_number[10000000];
	strcpy(cpy_number, number);

	for (i = 0; i < strlen(cpy_number); i++)
	{
		if (cpy_number[i] == cpy_number[i + 1])
		{			
			if (cpy_number[i] == cpy_number[i + 2])
			{
				number[j] = '3';
				j++;
				number[j] = cpy_number[i];
				j++;
				i += 2;
			}
			else
			{
				number[j] = '2';
				j++;
				number[j] = cpy_number[i];
				j++;
				i++;
			}
		}
		else
		{
			number[j] = '1';
			j++;
			number[j] = cpy_number[i];
			j++;
		}
	}

	printf("i = %zu, j = %d\n", i, j);
	number[j] = '\0';
}
