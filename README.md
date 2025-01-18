# GTO Poker Set Mining Analytics

I have been interested in poker theory for a little while now, so I thought I might as well try and create something related to Poker. For now, I am specifically looking into
a Texas Hold'em Poker variation, *Heads-Up Hold'em*.

## JFreeChart and Simuluations

To test out JFreeChart and how I want to run simulations, I created a couple charts:

![Pocket Pair Probability Chart](src/main/resources/pktpairprob.png)
![Suited Pair Probability Chart](src/main/resources/suitpairprob.png)

These charts are simply simulating the probability of being dealt a certain kind of hand. For example, if we look at the ***Pocket Pair Probability Chart***, 
the **x-axis** represents the number of simulations I ran while the **y-axis** represents the probability of being dealt a pocket pair 
(which improves as we increase the number of simulations). For instance, one simulation means I dealt a hand of two cards and then using some predicate 
(in this case, card rank equality), I kept track of whether we got a pocket pair. Keeping track of the number of pocket pairs and the number of simulations,
the probability is simply $\frac{\text{num pocket pairs}}{\text{num simulations}}$. This exact process was also used for the ***Suited Pair Probability Chart***.


Obviously, these are things we can prove mathematically without the use of simulations. It is, however, a pretty cool example of the central limit theorem and may be
helpful to statistics students who like poker[^1]. Also, JFreeChart and my current structure for simulations has been working decent, so I am planning on following a similar
pattern for future additions.

[^1]: For reference, the true probability of pocket pairs are 0.0588 and the true probability of suited pairs are 0.2353.
