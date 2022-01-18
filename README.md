# Sequence Search via Genetic Algorithm
## Introduction
Solving an optimization problem of placing the charges using genetic algorithm.
## Description
Input data for the problem is taken from `inputData.json` where all required data is stored.
## Usage
### Contents of `inputData.json`
`populationSize` - set the size of population.


`target` - set the ending criteria, `amount of ore`. When reached, results are printed and algorithm stops. Setting value to `0` results in finding the first available solution.

`initialRandomWeights` - set the coefficient that influences `random range` only during initial creation of the population. It helps yield better results at getting fit chromosomes. Setting value to `1` results in keeping the intended `random range`. Setting value to `[0.1-0.5]` is more preferable.

`weightLimit` - set the weight limit of `charges`. It is used during the `fitness calculation`. *"Tectonic activity can be caused by large amounts of charges".*

`oversizeChunkLimit` - set the limiting percentage of `oversized chunks`. It is used during the `fitness calculation`. *"A lot of ore is great, but when it's in large chunks, additional work is required to collect it".*

`generationCount` - set the limit amount of generations, at which algorithm stops.

`elitism` - set the amount of best chromosomes too keep.

`mutationChance` - set the chance of mutation occurence, it may happen for each gene/element of given sequence. Used during `crossover process`.

`multiply` - set the coeficient which influences occurence amount of fit chromosomes. It is based on `fitness score`. Helps in building mating pool needed for `crossover process` to happen. **For example:** if value is set to `30`, the fittest chromosome will appear in mating pool **30** times, while *less fit* chromosomes `(fit / fittest) * multiply` times.

`miningAreaProperties` - list that contains the data for `length of mining area` and applicable `charges`. Used during `fitness calculation`.

- `lengthOfMiningArea` - lenght of mining area (**in meters** or *whatever suits you*) where `charges` are used to gather `ore`.

- `charge` - list that contains specifications for each charge.

  - `range` - effective range of given charge
  - `weight` - how much it weighs
  - `amountOfOre` - how many tons of ore can be collected
  - `oversizeChunkPercentage` - percentage of oversized chunks
