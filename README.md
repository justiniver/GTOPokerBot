# GTO Poker Bot & Simulations

I have been interested in poker theory for a little while now, so I thought I might as well try and create something related to Poker. For now, I am specifically looking into
a Texas Hold'em Poker variation, *Heads-Up Hold'em*. So far, I have set up the simulations and now am working towards creating a GTO poker bot.

## JFreeChart and Monte Carlo Simuluations

To test out JFreeChart and how I want to run simulations, I created a couple charts:

![Pocket Pair Probability Chart](src/main/resources/pktpairprob.png)
![Suited Pair Probability Chart](src/main/resources/suitpairprob.png)

These charts are simply simulating the probability of being dealt a certain kind of hand. For example, if we look at the ***Pocket Pair Probability Chart***, 
the **x-axis** represents the number of simulations I ran while the **y-axis** represents the probability of being dealt a pocket pair. For instance, 
one simulation means I dealt a hand of two cards and then using some predicate (in this case, card rank equality), I kept track of whether we got a pocket pair. 
Then, probability is simply $\frac{\text{num pocket pairs}}{\text{num simulations}}$. This exact process was also used for the ***Suited Pair Probability Chart***.

Obviously, these are things we can prove mathematically without the use of simulations. It is, however, a pretty cool example of the central limit theorem and may be
helpful to statistics students who like poker[^1]. Also, JFreeChart and my current structure for simulations has been working decent, so I am planning on following a similar
pattern for future additions[^2].

## Flop Analysis Against Unrestricted Range

![Deuces Winning Flop Probability Chart](src/main/resources/deucesflopprob.png)

In this simulation, I analyzed how a certain hand plays against an unrestricted range on the flop. In this example, the small blind was dealt **pocket deuces** (pocket twos), and the big blind was given a random hand (with an unrestricted range).  A flop consisting of three cards was randomly dealt, and the small blind and big blind hands were simply the two cards in their hand, plus the three community cards on the board. This simulation was relatively easy to set up and run because there is only one combination of possible hands, namely because $\binom{N}{k} = \binom{\text{num hole cards } + \text{ num cards on board}}{5} = \binom{2 + 3}{5} = 1$. On the turn each player has $\binom{N}{k} = \binom{2 + 4}{5} = 6$ combinations of possible hands. On the river each player has $\binom{N}{k} = \binom{2 + 5}{5} = 21$ combinations of possible hands. This makes simulating entire games where there are 21 combinations of possible hands on the river for each player quite tedious. I am still trying to figure out an efficient way to do this. However, because determining which player is winning on the flop is the simple calculation of who has the better five-player hand, this simulation ran fast and without problem. It seems like **pocket deuces** are winning around **~66.5%** of the time.

![Aces Winning Flop Probability Chart](src/main/resources/acesflopprob.png)

It seems like **pocket aces** are winning around **~95.5%** of the time on the flop against an unrestricted range (random hand). However, it is important to note that this probabiity vastly overestimates the equity[^3] **pocket aces** really have against a random hand preflop. For this we will need to simulate the entire game, not just to the flop. Conversely, draw heavy hands (e.g., suited hands, connectors) will have their preflop equities underestimated using this flop analysis method.

![56 Suited Winning Flop Probability Chart](src/main/resources/56suitedflopprob.png)

It will be useful to use the hand **56 suited** to see how you can easily overvalue pocket pairs and undervalue draw heavy hands on the flop if you are not thinking in terms of equity. On the flop it seems like 56 suited is winning **~45%** of the time against an unrestricted range.

## Preflop Hand Equities

![Deuces Preflop Equity (UR)](src/main/resources/deucesshowdownprob.png)

I was able to figure out how to simulate games to the river. As mentioned before, simulating games to the river is a decent bit harder than just to the flop because we need to determine what the best 5-card hand is for each player (because there are 7 to choose from). Also, to make this nice graph and visually show the convergence, we are running this simulation $\sum_{n=0}^{995} 50 + 10n = 5004900$ times. This is because I start with $50$ simulations, then I simulate in increments of $10$ all the way to $10000$, which totals to $5004900$ simulations. On my computer this simulation takes around six minutes to fully run, which I think I can improve but for now I am just happy that it actually ran. As we can see from the graph, it seems like **pocket deuces** have **~51.5%** equity preflop against an unrestricted range. This is significantly less than the probability that deuces are winning on the flop.

![Aces Preflop Equity (UR)](src/main/resources/acesshowdownprob.png)

We can see a similar thing with **pocket aces**. From the graph it looks like **pocket aces** have **~85%** equity preflop against an unrestricted range[^4]. As we saw from the flop analysis, the probability of **pocket aces** winning on the flop is significantly higher.

![56 Suited Preflop Equity (UR)](src/main/resources/56suitedshowdownprob.png)

We see the opposite with **56 suited**. It seems like **56 suited** has **~47%** equity against an unrestricted range. Albeit small, there is a slight improvement in overall equity of **56 suited** compared to its probability winning on the flop. This is because **56 suited** is a draw heavy hand, which means that it has a higher chance of improving with the addition of cards (e.g., to a flush or a straight). 

## Running Equity Simulations

The code needed to create and run these equity convergence calculationns and create the graph is given below. You can run this in ````Main.kt```` and it should take ~5 minutes though you can modify this by running less or more simulations.

````java
    val cardStrings = CardStrings()
    val simulator = PokerEquitySim()

    simulator.runProgressiveSimulation(
        startTrials = 50,
        endTrials = 10000,
        step = 10,
        card1 = cardStrings.queenDiamond,
        card2 = cardStrings.tenDiamond,
        chartTitle = "Queen-Ten Suited Equity"
    )
````

## 10 Million Hands Winning Hand Rank Frequency

I set up a simulation where two poker bots play 10 million hands against each other. I set up both bots to use the check or call strategy, so all the bot does is check or call depending on what action is available (i.e., these hands always go to the river no matter what). I kept a record of the winning hand ranks for each poker game.

### Winning Hand Rank Frequency Table

| Winning Hand     | Frequency | Percentage |
|------------------|-----------|------------|
| High Card        | 610,362   | **6.10 %** |
| One Pair         | 3,754,871 | **37.55 %**|
| Two Pair         | 3,158,235 | **31.58 %**|
| Three of a Kind  | 721,952   | **7.22 %** |
| Straight         | 723,701   | **7.24 %** |
| Flush            | 524,394   | **5.24 %** |
| Full House       | 470,303   | **4.70 %** |
| Four of a Kind   | 30,903    | **0.31 %** |
| Straight Flush   | 5,279     | **0.05 %** |

### Winning Hand Rank Frequency Barchart

![10 Million Hands Winning Hand Rank Frequency Barchart](src/main/resources/handrankfreqbarchart10000000.png)

This data is somewhat useful if you are playing heads-up and want to know what the winning hand rank looks like on average. So, for example, the winning hand rank for heads-up is less strong than multiway poker, and roughly **~75%** of hands are won with a two pair or worse. If you are playing against someone who seems to never fold, than this is a useful statistic to keep in mind.




[^1]: For reference, the true probability of pocket pairs is 0.0588 and the true probability of suited pairs is 0.2353.
[^2]: Future additions includes the implementation of full game simulations as well as the implementation of counterfactual regret minimization among other useful metrics.
[^3]: Equity refers to the probability you are to win at showdown (i.e., how often a hand wins after all five community cards are dealt). If you are trying to figure out your equity on the flop, you need to make an educated guess on the strength of your opponents hand, and then calculate how many outs you have. You will then need to estimate how often those outs will appear on the turn and/or river. This is understandably quite hard to do, which is why we have computers.
[^4]: As some poker players may already know, this probability checks out. It is well known that aces have around 85% equity preflop against an unrestricted range, so this means that most likely my simulations are functioning correctly.
