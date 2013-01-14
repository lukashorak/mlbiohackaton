mlbiohackaton
=============
During the time I worked little bit more on the stock price retrieval library add two more functions:
getAllFromDate(Date fromDate)
getLastXDays(Integer days)

getAll()
getByDate(String year, String month, String day)
getByDuration(String start, String end)
getByMonth(String year, String month)

Portfolio Optimization
======================
I also make another things. The whole machine learning approach focus on predicting the future movement of the daily close price, but that is only part of the whole problem. The main goal of the game is:
1. Get the total asset value as high as possible at the end of simulation
2. Make it with as low risk as possible.
The both contradict together so there is the Modern Portfolio Theory to find the best point between return and risk.
So I make an algorithm which try to find the best stocks for the analysis.
Input: 
1.directory with files with historical stock prices
2.top-k = number of stocks with lowest SharpeRatio
3.portfolio-size = final number of stocks with lowest sum of correlation coefficients
Output:
list of n(portfolio-size) stocks, which produce the best candidates for the analysis
There is still quite a lot of things to improve:
- Different risk coefficient (SortinoRatio, ...)
- Query the historical data (just last month, last year, custom selected query)
- Apply some optimization algorithm to provide weights to the final stock candidates

Overlay Data
============
Inputs:
1.stock tick
2.day of interest
3.list of <keys> for the overlay data
Output:
map<keys, values>
Improvements:
1. method of interpolation (since the day of interest is 1 day, but the overlay data are usually once a month or once a quarter, it can have several methods how to get the value a) last know value b) next know value c) average d) some more sophisticated method of extrapolation) 
2. different data sources
