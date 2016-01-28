/* * * * * * * * * * * *
 * jsonnum2.c
 *
 * solves the second part of the question that jsonnum.c
 * began, using the json-c library
 * * * * * * * * * * * */

#include <stdio.h>
#include <stdlib.h>
#include "json-c-0.10/json.h"

int main(int argc, char* argv[])
{
	if (argc != 2)
	{
		printf("Usage: ./jsonnum2 <inputfile.txt>\n");
		return 1;
	}
	FILE* input = fopen(argv[1], "r");
	if (!input)
	{
		printf("Error opening file %s\n", argv[1]);
		return 2;
	}

	char* buffer = malloc(sizeof(char) * 100000);
	
	fgets(buffer, sizeof(char) * 100000, input);

	json_object* json_in = json_tokener_parse(buffer);

	return 0;
}
