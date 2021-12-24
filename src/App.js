import {Routes, Route, BrowserRouter as Router} from 'react-router-dom';
import WeekSelection from './codewalker-components/WeekSelection';
import CRIII from './codewalker-components/CRIII';

function App() {

  return (
      <Router>
        <Routes>
          <Route exact path='/' element={<WeekSelection/>}></Route>
          <Route path='/week11q1' element={<CRIII whichQuestion={1}/>}></Route>
          <Route path='/week11q2' element={<CRIII whichQuestion={2}/>}></Route>
        </Routes>
      </Router>
  );
}

export default App;
