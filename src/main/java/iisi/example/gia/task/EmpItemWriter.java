package iisi.example.gia.task;

import iisi.example.gia.dao.EmpRepository;
import iisi.example.gia.entity.EmpDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class EmpItemWriter implements ItemWriter<EmpDO> {

    @Autowired
    private EmpRepository empRepository;

    @Override
    public void write(Chunk<? extends EmpDO> chunk) throws Exception {
        log.info("Writing chunk: {}", chunk);
        chunk.getItems().forEach(item -> {
//            log.info("Writing item: {}", item);
            empRepository.save(item);
        });
    }
}
