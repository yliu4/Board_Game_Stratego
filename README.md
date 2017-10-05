# Board_Game_Stratego

## Introduction
Strategy is a game system, or a family of games, based upon the popular MiltonBradley game, Stratego® [1]. Stratego has its roots in much older games, the oldest being the Chinese game of Jungle, also known as “Fighting Animals” [2] A more recent ancestor is the French game of L’attaque. 
The standard game (referred to simply as “the game” unless a specific variation is under consideration) consists of two opposing armies, Red and Blue, that face off against each other on a bounded battlefield. The battlefield is a square 10x10 grid. 
Each army has forty pieces. Most of the pieces can be moved and some are stationary. One special piece is the Flag. When the Flag is captured by the opposing army, the game is over and the capturing player wins. The standard, official rules 
can be found on the Hasbro Corp. Stratego 2002 page

## Package Structure

Packages are used to expose a high-level structure of the software. There are two source folders, src and test that will each have almost identical package structures underneath them. 
 
###strategy
This is the root of the project. No files are contained directly in this package. Its sub packages contain the main components of the Strategy system.

###strategy.common
Classes, interfaces, and enumerations that are potentially used by multiple major components, but are not specific to any single component are rooted in this package.

###strategy.game
This package is the root of the strategy game controller component. The game controller is responsible for the actual running of a game. This package has sub packages for different version of Strategy and common elements that are used by all versions of Strategy.

###strategy.game.common
All elements that are part of the game controller and are used to implement some or all of the versions. Many of the elements in this package are either interfaces orabstract classes.

###strategy.game.version
This is the root package for all versions of Strategy. For example, there will be an alpha sub package that contains all of the elements specific to Alpha Strategy
