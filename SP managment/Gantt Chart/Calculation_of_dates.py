import re
from datetime import datetime, timedelta


def parse_tasks_and_calculate():
    # Enter start and end dates
    try:
        start_date_str = input("Enter start date in format DD/MM/YY: ")
        start_date = datetime.strptime(start_date_str, "%d/%m/%y")

        end_date_str = input("Enter end date in format DD/MM/YY: ")
        end_date = datetime.strptime(end_date_str, "%d/%m/%y")

        total_days = (end_date - start_date).days
        if total_days <= 0:
            print("End date must be after the start date.")
            return

    except ValueError:
        print("Invalid date format. Please try again.")
        return

    print("\nEnter project phases in the format: <number> <phase name> – <percentage>%")
    print(
        "For example:\n2.1 Define project scope and create scope statement – 30.5%\n2.2 Stakeholder analysis – 30.7%\n2.3 Create WBS project plan – 38.8%")
    print("Type 'stop' to finish input.\n")

    tasks = []
    while True:
        line = input("> ")
        if line.strip().lower() == "stop":
            break

        # Updated regular expression
        match = re.match(r'^\d+\.\d+\s+(.*?)\s+–\s+(\d+(\.\d+)?)%$', line.strip())

        if match:
            task_name = match.group(1).strip()
            try:
                percentage = float(match.group(2))  # Support for fractional numbers
                if 0 <= percentage <= 100:
                    tasks.append((task_name, percentage))
                else:
                    print("Percentage must be between 0 and 100.")
            except ValueError:
                print("Invalid percentage. Please try again.")
        else:
            print("Invalid format. Please check your input.")

    total_percentage = sum(task[1] for task in tasks)

    if round(total_percentage, 2) != 100.00:  # Check with floating-point precision
        print(f"\nThe total percentage is {total_percentage}%, but it must be 100%.")
        return

    print("\nDistributed tasks:")

    current_start_date = start_date

    # Create table header
    print("\nPhase Name\tStart Date\tEnd Date")
    for task_name, percentage in tasks:
        task_days = round(total_days * (percentage / 100))
        task_end_date = current_start_date + timedelta(
            days=task_days - 1)  # Subtract 1 day for correct calculation

        print(f"{task_name}\t{current_start_date.strftime('%d/%m/%y')}\t{task_end_date.strftime('%d/%m/%y')}")

        # Update the start date for the next task
        current_start_date = task_end_date + timedelta(days=1)


# Call the function
parse_tasks_and_calculate()
