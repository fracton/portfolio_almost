/*        Black Jack... the revenge of Jack
        By Jaron Lake
        5/04/11
        C++
        Desc: A Black Jack game, 1-7 players
*/
#include<iostream>
#include<list>
#include<string>
#include<vector>
#include<time.h>
#include<map>
using namespace std;
class Card
{ //A playing card with a suit & value
protected:
    string suitey;
    string cardey;
    string cardface;
    int value;
public:
    Card(string suitin=3D"blah", string cardin=3D"blagh", int valuein=3D3) =
{
        suitey=3Dsuitin;
        cardey=3Dcardin;
        value=3Dvaluein;
        cardface =3D "["+suitey+" " +cardey+"]";
    }
    string show()
    {    string temp =3D cardface;
        return temp;
    }
    friend class BlackJack;
    friend class Hand;
    friend class Deck;
};
class Cards
{private: //a vector of Cards...
    vector<Card>::iterator ci;
    vector<Card> cards;
public:
    void pop_back() { cards.pop_back(); }
    void push_back(Card &card){ cards.push_back(card);};
    friend class Hand;
    friend class Deck;
};
class Hand : private Cards
{protected: //The player's hand
    bool bust,stay;
    int total;
public:
    Hand()
    {    bust =3D false;
        stay =3D false;
    }
    int size(){return cards.size();}
    vector<Card>::iterator begin(){return cards.begin();}
    vector<Card>::iterator end(){return cards.end();}
    Card operator[](int &x){return cards[x];}
    int addCards();
    int getTotal() {
    return total;}
    friend class BlackJack;
};
int Hand::addCards()
{     int aces=3D0; //Get the total value of the hand
    total =3D 0;  //if it's a bust, try to change an ace from 11 to 1
    do{    for ( ci =3D cards.begin(); ci !=3D cards.end();++ci)
        {    total +=3D ci->value;
            if (ci->value=3D=3D11) ++aces;
        }
        ci =3D cards.begin();
        if (total > 21 && aces!=3D0)
        {    bool demotedAce=3Dfalse;
            while (!demotedAce && ci !=3D cards.end())
            {    if (ci->value =3D=3D 11)
                {    ci->value =3D 1;
                    demotedAce =3D true;
                    aces--;
                    total -=3D10;
                }
                ++ci;
            }
        }
    } while (total > 21 && (aces!=3D0));
    if (total > 21) bust=3Dtrue;
    return total;
};
class Deck : public Cards
{public: //a deck of cards
    Deck() {}
    void fillup();
    void showDeck();
    void shuffle();
    Card getCard()
    {    ci =3D --cards.end();
        return *ci;
    }
};
void Deck::fillup()
{//fill the deck with cards
    string suit[4] =3D {"=E2=99=A3", "=E2=99=A6", "=E2=99=A5", "=E2=99=A0"}=
;
    string card[13] =3D{"  ace", " king", " queen", " jack", "  ten", " nin=
e",
"eight", "seven", "  six", " five", " four", "three", "  two"};
    for (int si =3D 0; si!=3D4; si++)
    {    int value=3D11;
        for (int ci=3D0; ci!=3D13; ci++)
        {    if( (ci!=3D0) && (ci<5))value =3D 10;
            else if (ci >=3D5){
                value--;
                }

            Card *temp =3D new Card(suit[si], card[ci], value);
            cards.push_back(*temp);
        }
    }
}
void Deck::showDeck() //Not that you'd normally show the whole deck...
{    cout << "\nA deck o'cards...\n";
    cout << (int) cards.size()<<"<-deckkSize\n";
    Card temp;
    for (ci =3D cards.begin(); ci !=3D cards.end();ci++)
    {    Card bob =3D *ci;
        cout<< ci->show()<<ci->value;
    }
}
void Deck::shuffle() //take a Card of the back and stick it in a random spo=
t
{    srand(time(NULL));
    Card tempCard;
    for (int x=3D1; x!=3D1000000; x++) {
        int spot1 =3D (rand()%52);
        int spot2 =3D (rand()%52);
        tempCard =3D cards[spot1];
        cards[spot1] =3D cards[spot2];
        cards[spot2] =3D tempCard;
    }
}
class BlackJack  {//The actual Game
private:
    map <string, Hand > player;
    map <string, Hand >::iterator pi;
    bool playing;
    Deck deck;
public:
    BlackJack ()
    {    deck.fillup();
        deck.shuffle();
        //deck.showDeck();
    }
    void deal();
    void playGame();
    void getPlayers();
    void hit(map <string, Hand >::iterator &pi);
    void showCards(map <string, Hand >::iterator &pi, bool& playing);
    void checkBust(map <string, Hand >::iterator &pi);
    void showWinner();
};
void BlackJack::getPlayers()
{    //Sets up the players
    cout << "\nBlackJack\nby:Jaron Lake\nso...";
    int numPlayers=3D99;
    while (numPlayers>8||numPlayers<1) {
        cout << "How many players?[1-7]\n";
        cin >> numPlayers;
    }
    string name;
    Hand *hand;
    for (int x=3D1; x< numPlayers+1; ++x) {
        cout << "Type in the name of Player #"<<x<<": ";
        cin >> name;
        hand =3D new Hand;
        player.insert(make_pair(name, *hand));
    }
    hand =3D new Hand;
    name =3D " Dealer";
    player.insert(make_pair(name, *hand));
}
void BlackJack::playGame()
{    deal();
    bool playing =3D true;
    while (playing) { //Show the shindig...
        for (pi =3D player.begin(); pi !=3D player.end();++pi) showCards(pi=
,
playing);
        for (pi =3D player.begin(); pi !=3D player.end();++pi)
        {    playing =3D false;
            if (pi =3D=3D player.begin()) ++pi;
            cout << "\n";
            if (!(pi->second.bust || pi->second.stay))
            {    //if someone's not busted or staying: still playing
                playing =3D true;
                bool good =3D false;
                string choice;
                while (!good)
                {    showCards(pi, playing);
                    cout << pi->first << ", Would you like to hit or
stay?(h/s):";
                    cin >> choice;
                    good=3D(choice=3D=3D"h"||choice=3D=3D"s")?true:false;
                }
                if(choice=3D=3D"h")hit(pi);
                pi->second.addCards();
                showCards(pi, playing);
                if(choice=3D=3D"s")pi->second.stay=3Dtrue;
            }
        }
    }
    cout<<"Dealer's Turn";
    pi=3Dplayer.begin();
    pi->second.addCards();
    int total =3D pi->second.total;
    while(total < 17){ //dealer always stays at 17...
        hit(pi);
        pi->second.addCards();
        total =3D pi->second.total;
    }
    cout << "\n\n\nGame Over\n";
    for (pi =3D player.begin(); pi !=3D player.end();pi++) showCards(pi,
playing);
}
void BlackJack::showWinner() //figure out who won, show that
{   bool somebodyWon =3D false;
    int winnerTotal =3D 0;
    bool tie =3D false;
    map <string, Hand >::iterator winner;
    for (pi =3D player.begin(); pi !=3D player.end();pi++)
    {    if (!pi->second.bust)
        {    somebodyWon =3D true;
            tie =3D false;
            int total =3D pi->second.getTotal();
            if (total =3D=3D winnerTotal) tie =3D true;
            if (total > winnerTotal)
            {    winner =3D pi;
                winnerTotal =3D total;
                tie =3D false;
            }
        }
    }
    if (somebodyWon&&!tie)cout << "\n Winner is:"<< winner->first;
    else if (tie) {
        cout << "Push (tie) between ";
        for (pi =3D player.begin(); pi !=3D player.end();pi++)
        {    if (pi->second.getTotal() =3D=3D winnerTotal) cout << pi->firs=
t<<",
";
        }
    };
}
void BlackJack::hit(map <string, Hand >::iterator &pi)
{    Card card; //take a Card off the deck and give it to player pi
    card =3D deck.getCard();
    pi->second.push_back(card);
    deck.pop_back();
}
void BlackJack::deal()
{for (pi =3D player.begin(); pi !=3D player.end();pi++)
    {    hit(pi);
        hit(pi);
        pi->second.addCards();
    }
}
void BlackJack::showCards(map <string, Hand >::iterator &pi, bool &playing)
{        string name =3D pi->first; //Show a player's hand
        cout << name;
        for (int x =3D 0; x !=3D (int) pi->second.size(); x++) {
            string cardface;
            if (name=3D=3D" Dealer"&&x=3D=3D0&&playing=3D=3Dtrue) //if it's=
 the dealer,
hide first card
                cardface =3D "[xxxxxxx]";
            else cardface =3D pi->second[x].show();
            cout <<" "<<cardface<<pi->second[x].value;
        }
        if (pi->second.bust) cout << " BUST!";
        if (pi->second.stay) cout << " Staying!...";
        if(name=3D=3D" Dealer"&&playing)cout<<" Total=3D??\n";
        else cout << " Total=3D"<<pi->second.getTotal()<<"\n";
    }
int main () {
    BlackJack jack;
    jack.getPlayers();
    jack.playGame();
    jack.showWinner();
    int pause;
    cin >> pause;
    return 0;
}