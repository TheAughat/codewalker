import './question.css';
import CodeMirror from '@uiw/react-codemirror';
import {java, javaLanguage} from '@codemirror/lang-java';
import {languages} from '@codemirror/language-data';
import {useEffect} from 'react';

const Question = ({questionText, questionId, code, setCode, results}) => {
    
    useEffect(() => {
        const genericFillIn = `// write your import statements here\n\npublic class Main {\n  // Please do NOT add a main(String[] args) method\n\n  // your method goes here\n\n}`;
        setCode(genericFillIn);
    }, [questionId]);

    return (
        <div className='question-block'>
            <h3>Question {questionId}</h3>
            <div className='question-text new-line'>{questionText}</div>

            <CodeMirror value={code} 
            extensions={[java({ base: javaLanguage, codeLanguages: languages })]} 
            theme='light' 
            minHeight='20em'
            onChange={(value) => {
              setCode(value);
            }} className='editor'/>

            {(results)?<div className='results'>
                <p className='results-text new-line'>{results}</p>
            </div>:null}
        </div>
    );
}

export default Question;
