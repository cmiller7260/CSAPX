"""
bubbles.py

a program that randomly decides to draw or not to draw a circles child

functions:
- bubbles(radius:int, cutoff:float, Shrinkage: float, fanout:int) -> float: the main recursave function
-

author: Christopher Miller
"""


import turtle as t
import random
import math

FULL = 360




def bubbles(radius: int, cutoff: float, shrinkage: float, fanout: int, counter: int) -> float:
    """
    a recursive function to randomly decide wether or not to draw a circle based on the current level that it is in.

    :param radius: the radius of the circle
    :param cutoff: the number to raise the level by to get the probibility of the circle being drawn
    :param shrinkage: the amount of size the radius decreases by
    :param fanout: the amount of children may exist on the next level
    :param counter: the level of the circle being drawn
    :return: area of all the circles, however my program only returns the outermost function call because my
    function does not gow backwards.
    """
    COLORS = 'chartreuse', 'salmon', 'cyan', 'turquoise', 'lavender', 'tan'
    if (pow(cutoff,counter) < random.random()): # base case, do not draw a circle
        pass
    else: # draw a circle
        t.color(COLORS[counter%(len(COLORS)-1)]) #choose color based on the string and mod it by the length to avoid overflow
        t.begin_fill()
        t.circle(radius) #draw the circle
        t.end_fill()
        area = (math.pi*abs(radius*radius))
        for i in range (fanout): # loop fanout amout of times
            t.up()
            t.circle(radius,FULL/fanout)
            t.down()
            bubbles(int(-radius*shrinkage), cutoff, shrinkage, fanout, counter+1) # recursive call
        return area
        """does not return acuall area, only the 0th level area is returned becasue the recursive function
        is incremental, but the return function needs to be decremental but i cannot make it do that...
        """

def main() -> None:
    """
    main function
    :return:
    """
    fanout = input("Fanout[1 or more]? ") #get fanout from user
    shrinkage = input("Shrinkage[between 0 and 1]? ") #get shrinkage from user
    cutoff = input("Child probability? ") #gets the probability of the next child from the user
    radius=100
    counter=0
    area = 0
    t.speed(200)
    print("Total area drawn = " + str(bubbles(radius, float(cutoff), float(shrinkage), int(fanout), counter)))
    print("Click anywhere in the picture to terminate the program.")
    t.exitonclick()





if __name__ == '__main__':
    main()
