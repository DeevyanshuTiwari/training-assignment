
// Given Student data
const students = [
    {
        name: "Lalit",
        marks: [
            { subject: "Math", score: 78 },
            { subject: "English", score: 82 },
            { subject: "Science", score: 74 },
            { subject: "History", score: 69 },
            { subject: "Computer", score: 88 }
        ],
        attendance: 82
    },
    {
        name: "Rahul",
        marks: [
            { subject: "Math", score: 90 },
            { subject: "English", score: 85 },
            { subject: "Science", score: 80 },
            { subject: "History", score: 76 },
            { subject: "Computer", score: 92 }
        ],
        attendance: 91
    }
];

// total marks calculation
function calculateTotalMarks(student) {
    let total = 0;

    student.marks.forEach(sub => {
        total += sub.score;
    });

    return total;
}

console.log("Lalit total marks :",calculateTotalMarks(students[0]));
console.log("Rahul total marks :",calculateTotalMarks(students[1]));

//Average Calculation
function calculateAverage(student){
    const total = calculateTotalMarks(student);
    return total / student.marks.length;
}

console.log("Lalit Average",calculateAverage(students[0]));
console.log("Rahul Average",calculateAverage(students[1]));