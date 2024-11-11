package iisi.example.gia.task;

import iisi.example.gia.dao.DeptRepository;
import iisi.example.gia.entity.EmpDO;
import iisi.example.gia.vo.EmpVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Slf4j
public class EmpItemProcessor implements ItemProcessor<EmpVO, EmpDO> {

    @Autowired
    private DeptRepository deptRepository;

    @Override
    public EmpDO process(EmpVO item) throws Exception {
        log.info("Processing item: {}", item);
        EmpDO empDO = new EmpDO();
        empDO.setEname(item.getEname());
        empDO.setJob(item.getJob());
        empDO.setHiredate(LocalDate.parse(item.getHiredate()));
        empDO.setSal(item.getSal());
        empDO.setComm(item.getComm());
        empDO.setDeptDO(deptRepository.findById(item.getDeptno()).orElseThrow());
        if ("manager".equals(item.getJob())) {
            return empDO;
        }
        return null;
    }
}
