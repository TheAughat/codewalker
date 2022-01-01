import './server-connect.css';
import {useState} from 'react';

const ServerConnect = () => {
    const [status, setStatus] = useState('offline');
    const [btnStatus, setBtnStatus] = useState('Connect');

    async function makeConnection() {
        //
        if (status === 'online') {
            setBtnStatus('Connect');
            setStatus('offline');
            await fetch('http://localhost:8080/stop-server');
        }
        else if (status === 'offline') {
            const res = await fetch('http://localhost:8080/codewalker-check').then(resp => resp.json());
            if (res.result === 'cw server up and running') {
                setBtnStatus('Stop Server');
                setStatus('online');
            }
        }
    }

    return (
        <div className="server-connect">
            <p>Server status: {status}</p>
            <button className="btn connect-btn" onClick={async () => {
                makeConnection();
            }}>{btnStatus}</button>
        </div>
    );
};

export default ServerConnect;
