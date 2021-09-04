import React, {useState, useEffect} from "react";
import List from "../../List/List";

function UnSolvedSection({NotSolvedList, NtcList, TimeOverList, setPsList}) {
  const tag = "UnSolvedSection";
  const [SelectedTab, setSelectedTab] = useState("일부 TC 미통과");
  const navList = ["일부 TC 미통과", "풀이 실패", "시간 초과"];

  const onChange = (event) => {
    console.log("onChange", event.target.value);
    let nextType = event.target.value;
    setSelectedTab(nextType);
  };

  useEffect(() => {
    console.log(tag);
  }, []);

  return (
    <div>
      <div>
        <select className="unsolved__nav" onChange={(e) => onChange(e)}>
          {navList.map((tabType) => (
            <option key={tabType} value={tabType}>
              {tabType}
            </option>
          ))}
        </select>
      </div>
      <section className="unsolved">
        {SelectedTab === "일부 TC 미통과" && (
          <List data={NtcList} setList={setPsList} />
        )}
        {SelectedTab === "풀이 실패" && (
          <List data={NotSolvedList} setList={setPsList} />
        )}
        {SelectedTab === "시간 초과" && (
          <List data={TimeOverList} setList={setPsList} />
        )}
      </section>
    </div>
  );
}

export default UnSolvedSection;
