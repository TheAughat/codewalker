import './test-page.css';
import Question from './Question';
import {useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom';

const CRIII = ({whichQuestion}) => {

    let navigate = useNavigate();

    const [currentQuestion, setCurrentQuestion] = useState(whichQuestion);
    const [questionText, setQuestionText] = useState('');
    const [methodCall, setMethodCall] = useState('');

    const [code, setCode] = useState('');
    const [results, setResults] = useState('');

    useEffect(() => {
        retrieveQuestionData();
    }, [currentQuestion]);

    const retrieveQuestionData = async () => {
        // TODO: Use questionId to retrieve the question data from the backend
        // question text, method call
        const qID = 'wk11criiiqs' + currentQuestion;
        const res = await fetch('http://localhost:8080/get-question-data/' + qID).then(resp => resp.json());
        setMethodCall("hapless");
        setQuestionText(res[0]);
        setMethodCall(res[1]);
    };

    async function submitCode(codeToSubmit) {
        const qID = 'wk11criiiqs' + currentQuestion;
        const res = await fetch(`http://localhost:8080/${qID}/test-answer`, {
            method: 'POST',
            headers: {
                'Content-type': 'text/css',
            },
            body: codeToSubmit,
        }).then(resp => resp.json());
        console.log(res);

        let string = '';
        for (const element of res) {
            string += element + ' \n  \n '
        }
        setResults(string);
        if (!string.includes('failed') && !string.includes('rror') && !string.includes('xception')) {
            document.getElementsByClassName('results')[0].style.background = 'rgb(177, 255, 188)';
        }
        else {
            document.getElementsByClassName('results')[0].style.background = 'rgb(255, 120, 120)';
        }
    }


    return (
        <div className="test-page">
            Week 11, Coderunner III.
            <Question questionText={questionText} questionId={currentQuestion} code={code} setCode={setCode} results={results} methodCall={methodCall}/>

            <div className="buttons-panel">
                <button className='btn back-btn' onClick={() => {
                    setCurrentQuestion(1);
                    navigate('/week11q1');
                }}>Previous Question</button>
                <button className='btn test-btn' onClick={() => {
                    submitCode(code);
                }}>Test Code</button>
                <button className='btn next-btn' onClick={() => {
                    setCurrentQuestion(2);
                    navigate('/week11q2');
                }}>Next Question</button>
            </div>
        </div>
    );
}

export default CRIII;

