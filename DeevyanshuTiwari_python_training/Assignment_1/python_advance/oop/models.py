"""
Programs for:
40. Student Class
41. Car Class with Constructor
42. Inheritance (Person and Employee)
43. Encapsulation (Bank Class)
44. Polymorphism
"""


# Question 40
class Student:
    """Student class."""

    def __init__(self, name, age, branch):
        self.name = name
        self.age = age
        self.branch = branch

    def display_details(self):
        """Display student details."""
        print("Name:", self.name)
        print("Age:", self.age)
        print("Branch:", self.branch)


# Question 41
class Car:
    """Car class with constructor."""

    def __init__(self, brand, model):
        self.brand = brand
        self.model = model

    def display_car_details(self):
        """Display car details."""
        print("Brand:", self.brand)
        print("Model:", self.model)


# Question 42
class Person:
    """Parent class."""

    def __init__(self, name):
        self.name = name


class Employee(Person):
    """Child class."""

    def __init__(self, name, salary):
        super().__init__(name)
        self.salary = salary

    def display_employee_details(self):
        """Display employee details."""
        print("Name:", self.name)
        print("Salary:", self.salary)


# Question 43
class Bank:
    """Bank class demonstrating encapsulation."""

    def __init__(self, balance):
        self.__balance = balance

    def deposit(self, amount):
        self.__balance += amount

    def get_balance(self):
        return self.__balance


# Question 44
class Dog:
    """Dog class."""

    def speak(self):
        print("Dog says Woof")


class Cat:
    """Cat class."""

    def speak(self):
        print("Cat says Meow")


def main():
    """Run all OOP programs."""

    print("Question 40")
    student = Student(
        "Deevyanshu",
        22,
        "CSE"
    )
    student.display_details()

    print("\nQuestion 41")
    car = Car(
        "Toyota",
        "Fortuner"
    )
    car.display_car_details()

    print("\nQuestion 42")
    employee = Employee(
        "Rahul",
        50000
    )
    employee.display_employee_details()

    print("\nQuestion 43")
    account = Bank(10000)

    account.deposit(5000)

    print(
        "Balance:",
        account.get_balance()
    )

    print("\nQuestion 44")

    dog = Dog()
    cat = Cat()

    dog.speak()
    cat.speak()


if __name__ == "__main__":
    main()