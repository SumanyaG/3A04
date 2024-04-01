from random import randint


class Subject:

    number: int
    subscribers: list

    def __init__(self):
        self.subscribers = []

    def subscribe(self, observer):
        self.subscribers.append(observer)

    def unsubscribe(self, observer):
        self.subscribers.remove(observer)

    def notify(self):
        for observer in self.subscribers:
            observer.update()


class Observer():

    subject: Subject
    state: bool

    def __init__(self, subject: Subject):
        self.subject = subject
        self.subject.subscribe(self)

    def update(self):
        # Updates the state of the observer with whether the number is of its type or not
        pass


class MainModuleSubject(Subject):

    def __init__(self, number: int):
        self.number = number
        super().__init__()

    def determine_number_type(self) -> list:
        self.notify()

        types = []

        for observer in self.subscribers:
            if observer.state:
                types.append(str(observer))

        for observer in self.subscribers:
            self.unsubscribe(observer)

        return types


class OddObserver(Observer):

    def __init__(self, subject: Subject):
        super().__init__(subject)

    def update(self):
        self.state = self.isOdd()

    def isOdd(self) -> bool:
        return self.subject.number % 2 == 1

    def __str__(self) -> str:
        return 'Odd'


class EvenObserver(Observer):

    def __init__(self, subject: Subject):
        super().__init__(subject)

    def update(self):
        self.state = self.isEven()

    def isEven(self) -> bool:
        return self.subject.number % 2 == 0

    def __str__(self) -> str:
        return 'Even'


class PrimeObserver(Observer):

    def __init__(self, subject: Subject):
        super().__init__(subject)

    def update(self):
        self.state = self.isPrime()

    def isPrime(self) -> bool:
        n = self.subject.number

        if n > 1:
            for i in range(2, int(n ** 0.5) + 1):
                if n % i == 0:
                    return False
            return True
        return False

    def __str__(self) -> str:
        return 'Prime'


if __name__ == '__main__':
    number = randint(1, 1000)

    subject = MainModuleSubject(number)
    observer1 = OddObserver(subject)
    observer2 = PrimeObserver(subject)
    observer3 = EvenObserver(subject)

    print(f'Number: {number}')
    print(subject.determine_number_type())
