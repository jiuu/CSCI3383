import timeit
import random
class MinHeap:
    def __init__(self):
        self.heapList = [0]
        self.currentSize = 0

    def percolateUp(self, i):
        while i//2 > 0:
            if self.heapList[i] < self.heapList[i//2]:
                temp = self.heapList[i//2]
                self.heapList[i//2] = self.heapList[i]
                self.heapList[i] = temp
            i = i//2

    def insert(self, i):
        self.heapList.append(i)
        self.currentSize += 1
        self.percolateUp(self.currentSize)

    def percolateDown(self, i):
        while (i * 2) <= self.currentSize:
            child = self.minChild(i)
            if self.heapList[i] > self.heapList[child]:
                temp = self.heapList[i]
                self.heapList[i] = self.heapList[child]
                self.heapList[child] = temp
            i = child

    def minChild(self,i):
        if i * 2 + 1 > self.currentSize:
            return i * 2
        else:
            if self.heapList[i*2] < self.heapList[i*2+1]:
                return i * 2
            else:
                return i * 2 + 1

    def deleteMin(self):
        if len(self.heapList) == 1:
            return None
        value = self.heapList[1]
        self.heapList[1] = self.heapList[self.currentSize]
        self.heapList.pop(self.currentSize)
        self.currentSize -= 1
        self.percolateDown(1)
        return value

    def buildHeap(self, array):
        i = len(array) // 2
        self.currentSize = len(array)
        self.heapList = [0] + array
        while i > 0:
            self.percolateDown(i)
            i -= 1

    def heapSort(self):
        array = []
        tempHeap = self
        while tempHeap.currentSize > 0:
            array.append(tempHeap.deleteMin())
        return array        

    def genTest(self, t):
        array = []
        for i in range (t):
            array.append(random.randint(0,1))
        return array
                         
    def benchmark(self):

        for i in range (3):
            h = MinHeap()
            for x in range (100000):
                h.insert(random.randint(0,100))
            if i == 0:
                t = 100000
            if i == 1:
                t = 200000
            if i == 2:
                t = 300000
            array = h.genTest(t)
            start_time = timeit.default_timer()
            for j in range (len(array)):
                if array[j] == 0:
                    h.insert(random.randint(0,100))
                else:
                    h.deleteMin()
            finish_time = timeit.default_timer()
            print(finish_time - start_time)
            
        increment = 100000
        
        for y in range (3):
            h = MinHeap()
            for q in range(increment):
                h.insert(random.randint(0,100))
            heapSort_time = timeit.default_timer()
            h.heapSort()
            print(timeit.default_timer( ) - heapSort_time)
            increment += 100000

