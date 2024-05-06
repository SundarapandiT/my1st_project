#include<stdio.h>
#include<conio.h>
int a[10][10],b[10][10],c[10][10];
int m,n,p,q;
void main()
{
  int i,j,k;
  clrscr();
  printf("Enter no of row and col in Matrix A: ");
  scanf("%d%d",&m,&n);

  printf("\nEnter no of row and col in Matrix B: ");
  scanf("%d%d",&p,&q);
  if (n==p)
  {
   printf("\nTestCase 1 is success \n Enter Matrix A elements\n");
   for(i=0;i<m;i++)
   {
   for (j=0;j<n;j++)
   {
    scanf("%d",&a[i][j]);
   }
  }
  printf("\nEnter Matrix B elements \n");
     for(i=0;i<p;i++)
   {
   for (j=0;j<q;j++)
   {
    scanf("%d",&b[i][j]);
   }
  }
  printf("Matrix Multiplication:\n");
  for(i=0;i<m;i++)
  {
  for(j=0;j<q;j++)
  {
    c[i][j]=0;
   for(k=0;k<n;k++)
   {
     c[i][j]+=a[i][k]*b[k][j];
   }
   printf("%d ",c[i][j]);
  }
  printf("\n");
 }
}
else
{
 printf("\nTestCase:1- Failed!!! Matrix A col and Matrix B row must be equal");
}
getch();
}
