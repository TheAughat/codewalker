import './server-connect.css';
import {useState, useEffect} from 'react';

const ServerConnect = () => {
    const [status, setStatus] = useState('offline');
    const [btnStatus, setBtnStatus] = useState('Connect');

    useEffect(() => {
        makeConnection();
    }, []);

    async function makeConnection() {
        //
        if (status === 'online') {
            setBtnStatus('Connect');
            setStatus('offline');
            document.getElementsByClassName('status-text')[0].style = 'color:#cc0000;';
            await fetch('http://localhost:8080/stop-server');
        }
        else if (status === 'offline') {
            document.getElementsByClassName('status-text')[0].style = 'color:#cc0000;';
            const res = await fetch('http://localhost:8080/codewalker-check').then(resp => resp.json());
            if (res.result === 'cw server up and running') {
                setBtnStatus('Stop Server');
                setStatus('online');
                document.getElementsByClassName('status-text')[0].style = 'color:#00802b;';
            }
        }
    }

    return (
        <div className="server-connect">
            <p className='status-text'>Server status: {status}</p>
            <button className="btn connect-btn" onClick={async () => {
                makeConnection();
            }}>{btnStatus}</button>
        </div>
    );
};

export default ServerConnect;
