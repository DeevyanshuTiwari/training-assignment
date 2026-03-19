
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

// console.log("Lalit total marks :",calculateTotalMarks(students[0]));
// console.log("Rahul total marks :",calculateTotalMarks(students[1]));

//Average Calculation
function calculateAverage(student){
    const total = calculateTotalMarks(student);
    return total / student.marks.length;
}

// console.log("Lalit Average",calculateAverage(students[0]));
// console.log("Rahul Average",calculateAverage(students[1]));

// Subject-wise Highest Score

function subjectHighestScore(students){

    let subjects = students[0].marks.map(m => m.subject);

    subjects.forEach(subject => {

        let highest = 0;
        let topper = "";

        students.forEach(student => {
            let sub = student.marks.find(m => m.subject === subject);

            if(sub.score > highest){
                highest = sub.score;
                topper = student.name;
            }
        });

        console.log(`Highest in ${subject}: ${topper} (${highest})`);
    });
}
// subjectHighestScore(students);

// Subject-wise Average

function subjectAverage(students){

    let subjects = students[0].marks.map(m => m.subject);

    subjects.forEach(subject => {

        let total = 0;

        students.forEach(student => {
            let sub = student.marks.find(m => m.subject === subject);
            total += sub.score;
        });

        let avg = total / students.length;

        console.log(`Average ${subject} Score: ${avg}`);
    });
}

// subjectAverage(students);

// Topper Identification
function findTopper(students){

    let maxMarks = 0;
    let topper = "";

    students.forEach(student => {
        let total = calculateTotalMarks(student);

        if(total > maxMarks){
            maxMarks = total;
            topper = student.name;
        }
    });

    console.log(`Class Topper: ${topper} with ${maxMarks} marks`);
}

// findTopper(students);

// Grade Logic

function calculateGrade(student){

    let avg = calculateAverage(student);

    if(avg >= 85) return "A";
    else if(avg >= 70) return "B";
    else if(avg >= 50) return "C";
    else return "Fail";
}

console.log("Lalit Grade :",calculateGrade(students[0]));
console.log("Rahul Grade :",calculateGrade(students[1]));