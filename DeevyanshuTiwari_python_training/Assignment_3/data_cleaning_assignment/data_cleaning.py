"""
Assignment 3: Data Cleaning

Tasks:
1. Detect missing values.
2. Replace missing Age with mean.
3. Replace missing Salary with 0.
"""

import pandas as pd


DEFAULT_SALARY: int = 0


def create_employee_dataframe() -> pd.DataFrame:
    """
    Create employee DataFrame with
    missing values.
    """

    employee_data = {
        "Name": [
            "Rahul",
            "Priya",
            "Anuj"
        ],
        "Age": [
            25,
            None,
            29
        ],
        "Salary": [
            30000,
            40000,
            None
        ]
    }

    return pd.DataFrame(
        employee_data
    )


def display_missing_values(
        employee_dataframe: pd.DataFrame
) -> None:
    """
    Display missing values.
    """

    print("\nMissing Values:")

    print(
        employee_dataframe.isnull()
    )


def fill_missing_age(
        employee_dataframe: pd.DataFrame
) -> None:
    """
    Replace missing Age with mean age.
    """

    average_age = (
        employee_dataframe["Age"]
        .mean()
    )

    employee_dataframe["Age"] = (
        employee_dataframe["Age"]
        .fillna(average_age)
    )


def fill_missing_salary(
        employee_dataframe: pd.DataFrame
) -> None:
    """
    Replace missing Salary with 0.
    """

    employee_dataframe["Salary"] = (
        employee_dataframe["Salary"]
        .fillna(DEFAULT_SALARY)
    )


def main() -> None:
    """
    Execute all tasks.
    """

    employee_dataframe = (
        create_employee_dataframe()
    )

    print("Original DataFrame:")

    print(employee_dataframe)

    display_missing_values(
        employee_dataframe
    )

    fill_missing_age(
        employee_dataframe
    )

    fill_missing_salary(
        employee_dataframe
    )

    print("\nCleaned DataFrame:")

    print(employee_dataframe)


if __name__ == "__main__":
    main()