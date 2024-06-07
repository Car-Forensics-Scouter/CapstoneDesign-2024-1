function OneToOne(props) {
    const fontSize = props.fontSize;
    
    return (
        <div className={props.className}>
            <div className="detail-button">
                <i class="fa-solid fa-circle-info"/>
                <span className="tooltip">{props.tooltip}</span>
            </div>
            <div className="title" style={{fontSize: fontSize}}>{props.title}</div>
            <div className="avg-value">{props.value}</div>
            <div className="unit">{props.unit}</div>
        </div>
    );
}

export default OneToOne;