import './week-selection.css';
import './WeekBlock';
import WeekBlock from './WeekBlock';

function WeekSelection() {
    return (
        <div className='week-selection'>
            <h1>CodeWalker</h1>
            <br></br>
            <WeekBlock week={'Week 11'} number={'CRIII'} link={'/week11q1'}/>
            <WeekBlock week={'Week XX'} number={'CRIV'}/>
        </div>
    );
}

export default WeekSelection;
