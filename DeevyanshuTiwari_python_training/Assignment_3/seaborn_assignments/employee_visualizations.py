"""
Assignment 6: Seaborn Visualizations

Tasks:
1. Barplot -> Department vs Salary
2. Boxplot -> Salary Distribution
3. Heatmap -> Correlation between Age and Salary
"""

import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt


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


def create_department_salary_barplot(
        employee_dataframe: pd.DataFrame
) -> None:
    """
    Create Department vs Salary barplot.
    """

    plt.figure(
        figsize=(6, 4)
    )

    sns.barplot(
        data=employee_dataframe,
        x="Department",
        y="Salary"
    )

    plt.title(
        "Department vs Salary"
    )

    plt.show()


def create_salary_boxplot(
        employee_dataframe: pd.DataFrame
) -> None:
    """
    Create salary distribution boxplot.
    """

    plt.figure(
        figsize=(6, 4)
    )

    sns.boxplot(
        y=employee_dataframe["Salary"]
    )

    plt.title(
        "Salary Distribution"
    )

    plt.show()


def create_correlation_heatmap(
        employee_dataframe: pd.DataFrame
) -> None:
    """
    Create correlation heatmap.
    """

    correlation_matrix = (
        employee_dataframe[
            ["Age", "Salary"]
        ].corr()
    )

    plt.figure(
        figsize=(5, 4)
    )

    sns.heatmap(
        correlation_matrix,
        annot=True
    )

    plt.title(
        "Age and Salary Correlation"
    )

    plt.show()


def main() -> None:
    """
    Execute all visualizations.
    """

    employee_dataframe = (
        create_employee_dataframe()
    )

    create_department_salary_barplot(
        employee_dataframe
    )

    create_salary_boxplot(
        employee_dataframe
    )

    create_correlation_heatmap(
        employee_dataframe
    )


if __name__ == "__main__":
    main()