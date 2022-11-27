# This Python program sorts unsorted arrays of integers given by
# the program itself, as well as given through text files
# using multiple different sorting algorithms 
#
# Sorting algorithms implemented
# - Bubble
# - TODO: Selection
# - TODO: Merge
# - TODO: Insertion
# - TODO: Quick
#
# Created By: Josh Johnson

from os import path # used to get relative path for text files

# bubble sort algorithm
def bubble_sort(array):
    # if the array is already sorted
    swapped = False

    # traverse through all array elements
    for i in range(len(array) - 1):
        # previous i elements are already in place
        for j in range(0, len(array) - i - 1):
            # swap if the current element is greater than the next element
            if array[j] > array[j + 1]:
                swapped = True
                array[j], array[j + 1] = array[j + 1], array[j]
         
        if not swapped:
            return

# prints the contents of the given integer array to stdout
def print_array(array, array_id, ordered, sorting_algorithm):
    if ordered:
        print("Array with ID %d after %s Sort algorithm applied:" % (array_id, sorting_algorithm), end=" ") # end parameter prevents new line
    else:
        print("Array with ID %d before %s Sort algorithm applied:" % (array_id, sorting_algorithm), end=" ") # end parameter prevents new line
    
    for i in range(len(array)):
        if i == len(array) - 1:
            print("%d" % array[i])
        else:
            print("%d" % array[i], end=", ")

# main function - controls operations and flow of the program
def main():
    array_id = 1

    # applying sorting algorithms
    # bubble sort - size and values are given by program
    array_one = [3, 66, 45, 23, 9, 0, 11, 2, 7, 49, 21]
    print_array(array_one, array_id, False, "Bubble")
    bubble_sort(array_one)
    print_array(array_one, array_id, True, "Bubble")
    
# begins program by calling the main function
main()