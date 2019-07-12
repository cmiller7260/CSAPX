"""
merchants.py

a program that finds the median location for a named list generated off of a file that the user specifies
along with the search type, the number of merchants, the total elapsed time and the sum of distances

functions:
- sum_of_int(data: List[int], loc: int) -> int: gets the sum of distances to the new store location
- select_sort(data: List[int], index: int): preforms select sort on the list
- read_merchant (filename: str) -> List[str]: takes input from a file and adds them to a named list
- quick_sort(data: List[int]) -> List[int]: preforms the quicksort algorithm
- _partition(data: List[int], pivot: int) -> Tuple[List[int], List[int], List[int]]: preforms the partition for the quicksort algorithm


author: Christopher Miller
"""
import sys
import collections # namedtuple
from typing import List     # List
from typing import Tuple    # Tuple
import time


Merchants = collections.namedtuple('Merchants', ('name', 'location'))

def main() -> None:
    id = sys.argv #system arguments
    merchant = read_merchant(id[2]) # make the namedtuple
    length=(len(merchant))
    index = ((int(len(merchant))) // 2)

    if id[1] == 'slow': # use quicksort or quickselect
        start = time.clock()
        sort_data = quick_sort(merchant)
        sort_data = sort_data[index]
        etime = time.clock()-start
    if id[1] == 'fast':
        start = time.clock()
        sort_data = select_sort(merchant, index)
        etime = time.clock() - start
    print('Search type: ',id[1]) # print statments
    print('Number of merchants: ',length)
    print('Ellapsed time: ', etime, ' seconds')
    print('Optimal store location: ', sort_data)
    print('Sum of distances: ', sum_of_int(merchant, int(sort_data[1])))


def sum_of_int(data: List[int], loc: int) -> int:
    """
    find the sum of distances to the median location
    :param data: the list of merchants
    :param loc: the location of the new store
    :return: the sum
    """
    summ=0
    for i in range (len(data)):
        summ += abs(int(data[i][1])-loc)
    return summ

def select_sort(data: List[int], index: int):
    """
    the select sort algorithm, preforms a quickselect and returns the median value
    :param data: the list
    :param index: the median value to find
    :return:
    """
    less, greater = [], []
    count = 0
    pivot = (data[0]) # the pivot location
    for i in range (len(data)): # loop and move the smaller values into a list called less and the larger valuse into a list called greater, or increment counter if equal.
        if (int(data[i][1]) < int(pivot[1])):
            less.append(data[i])
        elif (int(data[i][1]) > int(pivot[1])):
            greater.append(data[i])
        else:
            count += 1
    lower = (int(len(less)))
    upper = (int(len(greater)))
    if (index >= lower and index < (lower+count)): # determin which path to go down.
        return pivot
    elif index < lower:
        return select_sort(less, index)
    else:
        index = (index-lower-count)
        return select_sort(greater, index)


def read_merchant (filename: str) -> List[str]:
    """
    reads the input file and appends to a namedtuple
    :param filename: the file to read
    :return: the named list gathered from the file
    """
    merchant = list()
    with open( filename ) as f:
        for line in f:
            fields = line.split( ' ' )
            merchant.append( Merchants(
                name = fields[0],
                location = fields[1][:-1]
            ))
    return merchant

def quick_sort(data: List[int]) -> List[int]:
    """
    Performs a quick sort and returns a newly sorted list
    :param data: The data to be sorted (a list)
    :return: A sorted list
    """
    if len(data) == 0:
        return []
    else:
        pivot = int(data[0][1]) #changed to work for a namedtuple
        less, equal, greater = _partition(data, pivot)
        return quick_sort(less) + equal + quick_sort(greater)

def _partition(data: List[int], pivot: int) -> Tuple[List[int], List[int], List[int]]:
    """
    Three way partition the data into smaller, equal and greater lists,
    in relationship to the pivot
    :param data: The data to be sorted (a list)
    :param pivot: The value to partition the data on
    :return: Three list: smaller, equal and greater
    """
    less, equal, greater = [], [], []
    for i in range (len(data)):
        if int(data[i][1]) < pivot:
            less.append(data[i])
        elif int(data[i][1]) > pivot: # changed code to work with a named tuple
            greater.append(data[i])
        else:
            equal.append(data[i])
    return less, equal, greater


if __name__ == '__main__':
    main()
