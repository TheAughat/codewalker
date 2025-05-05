import './week-selection.css';
import { useNavigate } from 'react-router-dom';

const WeekBlock = ({week, number, link}) => {
    let navigate = useNavigate();

    return (
        <div className="week-block">
            <h2>{week}</h2>
            <h3>{number}</h3>
            <button className='link' onClick={() => navigate(link)}>Go to page</button>
        </div>
    );
}

export default WeekBlock;
