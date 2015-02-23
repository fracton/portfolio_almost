/*  magick_square.cpp
    CS 140 -> C++
    Jaron Lake
    March forth, 2011
    and the date's 3/4/11
    Desc: Generates a "magic" square wherein all of the diagonals, rows, and
columns
        add up to the same number, using all numbers 1-n^2 once.
*/
#include"stdafx.h"
#include<iostream>
#include<string>
#include<vector>
using namespace std;
class MagicSquare
{private:
    vector< vector<int> > square;
    int x,y;    //vector index
public:
    MagicSquare(int size=0){    }
    void spot(int, int);
    void makeSquare(int);
    void display();
};    //puts the # on the spot, inc/dec the index
void MagicSquare::spot(int i, int size)
{
        if (square[x][y]==0) //if the spot's blank, put our # in it
    {    square[x][y]= i; //and move to the next spot
        x++;
        y--;
        if (y < 0) { y= size;}  //keeping in the boundaries
        if (x > size) { x= 0;}
    }else
    {    y+=2;
        x--;                    //go back and down
        if (y==(size+2)) {y= 1;}//wrap around
        if (y > size) { y= 0;}
        if (x < 0) { x= size;}
        spot(i, size);          //try the next spot...
    }
}
void MagicSquare::makeSquare(int size)
{   square.resize(size, vector<int>(size, 0));
    cout<< "size: " << size<<"\n";
    for (y=0; y < size; y++)
    {    square[y].resize(size,0);
        for (x=0; x < size; x++)
        {    square[x][y]=0;}
    }
    y= 0;
    x= (size/2);
    int numberOfNumbers= size*size;
    numberOfNumbers++;
    size--;                //for the end o'the vector
    for (int i = 1; i < numberOfNumbers; i++)
    {    spot(i, size); }
}
void MagicSquare::display()
{   int size= square.size();
    int x, y;
    for (y=0; y < size; y++)
    {   for (x=0; x < size; x++)
        {   cout.width(3);
            cout<<square[x][y]<<"|";
        }
        cout<<"\n";
    }
    cout <<"\n";
}
int main()
{   MagicSquare hip;
    for (int i=3; i < 16; i+=2)
    {    hip.makeSquare(i);
        hip.display();
    }
    system("pause");
    return 0;
};