"""
Assignment 4: Data Analysis

Tasks:
1. Find average salary by department.
2. Find maximum salary by department.
3. Count employees per department.
"""

import pandas as pd


def create_employee_dataframe() -> pd.DataFrame:
    """
    Create employee DataFrame.
    """

    employee_data = {
        "Name": [
            "Rahul",
            "Priya",
            "Amit",
            "Anuj"
        ],
        "Age": [
            25,
            30,
            28,
            35
        ],
        "Department": [
            "HR",
            "IT",
            "Finance",
            "IT"
        ],
        "Salary": [
            30000,
            50000,
            45000,
            60000
        ]
    }

    return pd.DataFrame(
        employee_data
    )


def find_average_salary_by_department(
        employee_dataframe: pd.DataFrame
) -> None:
    """
    Display average salary by department.
    """

    average_salary = (
        employee_dataframe
        .groupby("Department")["Salary"]
        .mean()
    )

    print(
        "\nAverage Salary By Department:"
    )

    print(average_salary)


def find_max_salary_by_department(
        employee_dataframe: pd.DataFrame
) -> None:
    """
    Display maximum salary by department.
    """

    maximum_salary = (
        employee_dataframe
        .groupby("Department")["Salary"]
        .max()
    )

    print(
        "\nMaximum Salary By Department:"
    )

    print(maximum_salary)


def count_employees_by_department(
        employee_dataframe: pd.DataFrame
) -> None:
    """
    Count employees in each department.
    """

    employee_count = (
        employee_dataframe
        .groupby("Department")["Name"]
        .count()
    )

    print(
        "\nEmployee Count By Department:"
    )

    print(employee_count)


def main() -> None:
    """
    Execute all tasks.
    """

    employee_dataframe = (
        create_employee_dataframe()
    )

    print(
        "Employee DataFrame:"
    )

    print(employee_dataframe)

    find_average_salary_by_department(
        employee_dataframe
    )

    find_max_salary_by_department(
        employee_dataframe
    )

    count_employees_by_department(
        employee_dataframe
    )


if __name__ == "__main__":
    main()