/* * * * * * * * * * * *
 * adventcoin.c
 *
 * searches for an integer (incrementing from 1)
 * which, when appended onto a given key, produces at least a given number of 
 * leading zeros in an MD5 hash (at current implementation, can only test for
 * five or six)
 * * * * * * * * * * * */

#include <stdio.h>
#include <string.h>
#include <math.h>
#include <stdlib.h>
#include "md5.h"

int main(int argc, char* argv[])
{
	if (argc != 3)
	{
		printf("Usage: ./adventcoin.c <key> <number of zeros>\n");
		return 1;
	}
	
	// this is terrible implementation, but i'm lazy today and 
	// might improve this later
	// hey, at least it solves the problem at hand...
	int zeros = atoi(argv[2]);
	if (zeros < 5 || zeros > 6)
	{
		printf("Can only check for 5 or 6 leading zeros\n");
		return 2;
	}
	
	char* key = argv[1];

	// use two MD5_CTX - init for initial update,
	// tmp to be updated with each iteration of i and tested
	MD5_CTX ctx_init;
	MD5_CTX ctx_tmp;
	unsigned char result[16] = {1,1,1};
	long long n = 0;
	int i;
	char n_str[15];

	MD5_Init(&ctx_init);
	MD5_Update(&ctx_init, key, strlen(key));

	if (zeros == 5)
	{
		while (result[0] != 0x00 ||
		       result[1] != 0x00 ||
		       result[2] > 0x10)
		{
			n++;
			snprintf(n_str, 14, "%lld", n);
			ctx_tmp = ctx_init;

			MD5_Update(&ctx_tmp, n_str, floor(log10(abs(n))) + 1);

			MD5_Final(result, &ctx_tmp);
		}
	}
	else if (zeros == 6)
	{
		while (result[0] != 0x00 ||
		       result[1] != 0x00 ||
		       result[2] != 0x00)
		{
			n++;
			snprintf(n_str, 14, "%lld", n);
			ctx_tmp = ctx_init;

			MD5_Update(&ctx_tmp, n_str, floor(log10(abs(n))) + 1);

			MD5_Final(result, &ctx_tmp);
		}
	}

	printf("Number: %lld\n", n);

	printf("Hash: ");
	for (i = 0; i < 16; i++)
	{
		printf("%02x", result[i]);
	}
	printf("\n");
	return 0;
}
