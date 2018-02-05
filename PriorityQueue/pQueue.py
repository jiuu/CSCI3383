import minHeap
import random
import timeit
class MinPriorityQueue:
    def __init__(self):
        self.heap = minHeap.MinHeap()
        
    def empty(self):
        if self.heap.currentSize <= 0:
            return True
        else:
            return False

    def findMin(self):
        return self.heap.heapList[1]

    def extractMin(self):
        return self.heap.deleteMin()

    def insert(self, v, kv):
        self.heap.insert((v,kv))

    def delete(self, v):
        for x in range(1,self.heap.currentSize+1):
            (i,j) = self.heap.heapList[x]
            if j == v:
                temp = self.heap.heapList[x]
                self.heap.heapList[x] = self.heap.heapList[self.heap.currentSize]
                self.heap.heapList.pop(self.heap.currentSize)
                self.heap.currentSize -= 1
                self.heap.percolateDown(x)
                return (i,j)
        return False

    def decreaseKey(self, v, kv):
        for x in range(1,self.heap.currentSize+1):
            (i,j) = self.heap.heapList[x]
            if j == v:
                self.heap.heapList[x] = (kv, v)
                self.heap.percolateUp(x)
                return True
        return False
                
    def meld(self, q):
        for x in range(1,q.heap.currentSize+1):
            (i,j) = q.heap.heapList[x]
            self.heap.insert((i,j))

    def genTest(self, t):
        array = []
        for i in range (t):
            array.append(random.randint(0,1))
        return array
    
    def benchmark(self):
        for i in range (3):
            h = MinPriorityQueue()
            for x in range (100000):
                h.insert(random.randint(0,100),random.randint(0,100))
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
                    h.insert(random.randint(0,100),random.randint(0,100))
                else:
                    h.extractMin()
            finish_time = timeit.default_timer()
            print(finish_time - start_time)

        t = 100000
        
        for i in range (3):
            h = MinPriorityQueue()
            for x in range (t):
                h.insert(random.randint(0,100),random.randint(0,100))
            start_time = timeit.default_timer()
            for j in range (100):
                h.delete(j)
            print(timeit.default_timer() - start_time)
            t += 100000
                
        
        

