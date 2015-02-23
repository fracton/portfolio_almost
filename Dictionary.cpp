                                                                                                                                                                                                                                                               /*    Dictionarry.cpp
 CS 140 -> C++
 Jaron Lake
 March 23(what does it mean?), 2011
 Desc: Dictionary, reads in a word file allows
    adding, erasing, saving, listing... yay
 */
#include<iostream>
#include<string>
#include<list>
#include<vector>
#include<fstream>
using namespace std;
bool compare(string str1, string str2)
{
    int x=0;
    while ( (x<str1.length()) && (x<str2.length()) )
    {    if (tolower(str1[x])<tolower(str2[x])) return true;
        else if (tolower(str1[x])>tolower(str2[x])) return false;
        x++;
    }
    if (str1.length()<str2.length()) return true;
    else return false;
}
class Dictionary
{
private:
    list<string> words;
    list<string>::iterator wi;
    int found;
public:
    Dictionary() //Construtoratomitron
    {    char* filename = "wordlist.txt";
        loadWords(filename);
    }
    void loadWords(char * file);
    void show() {for (wi = words.begin(); wi != words.end(); ++wi) cout <<
*wi << "\n"; }
    bool find(string, bool);
    bool equals(string, string);
    void add(string);
};
bool Dictionary::find(string word, bool kill=false)
{    //Finds a word, if kill is true it erases it.
    for (wi = words.begin(); wi != words.end(); ++wi)
    {    if (equals(*wi, word))
        {    if (kill) words.erase(wi);
            return true;
        }
    }
    return false;
}
bool Dictionary::equals(string str1, string str2)
{    // True if str1 equals str2
    int x=0;
    if (str1.length()!=str2.length()) return false; //different lengths
    while ( x<str1.length())
    {    if (tolower(str1[x])!=tolower(str2[x])) return false;
        x++;
    }
    return true;
}
void Dictionary::loadWords(char*filename)
{    // Load words from the file into the list
    ifstream infile;
    infile.open(filename);
    if(infile.fail())
    {    cerr<<"Error opening file: "<<filename<<endl;
        exit(0);
    }
    string tempWord;
    while (!infile.eof())
    {
        infile >> tempWord;
        words.push_back(tempWord);
    }
    words.sort(compare);
}
void Dictionary::add(string word)
{    words.push_back(word); //Adds new word and sorts
    words.sort(compare);
}
int main()
{     Dictionary webster;
    string bob = " ";
    string word;
    while (bob!="x")
    {    cout << "(f)ind, (a)dd, (e)rase,(s)how dictionary, e(x)it: ";
        cin >> bob;
        switch (bob[0]) {
            case 's':
                webster.show();
                break;
            case 'f':
                cout << "Find what? ";
                cin >> word;
                if (webster.find(word)) cout << "Yep, it's there \n";
                else cout << "It's not there.\n";
                break;
            case 'a':
                cout << "Add what? ";
                cin >> word;
                if (webster.find(word)) cout << "It's already there \n";
                else webster.add(word);
                break;
            case 'e':
                cout << "Erase what? ";
                cin >> word;
                while (webster.find(word, true)) cout<<"erasingggg\n";
                break;
            default:
                cout << "ouch!\n";
                break;
        }
    }
    return 0;
};