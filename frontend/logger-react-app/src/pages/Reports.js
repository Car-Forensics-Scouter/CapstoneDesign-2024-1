import '../App.css';

import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';

function Reports() {

    return  (
        <div className="Reports">
            <div>
                타이틑이랑 다운로드 부분
            </div>
            <div>분리선 부분</div>
            <div>
                <div className="left">
                    왼쪽 부분 - 차량 사진, 차량 정보, 그 외 속성들
                </div>
                <div className="right">
                    오른쪽 부분 - 지도, 그 외 속성들
                </div>
            </div>
        </div>
    );
}

export default Reports;