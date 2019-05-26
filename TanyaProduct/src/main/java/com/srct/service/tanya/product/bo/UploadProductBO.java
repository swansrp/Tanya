/**
 * Title: UploadProductBO
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-26 11:32
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.product.bo
 */
package com.srct.service.tanya.product.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UploadProductBO extends BaseBO {
    private MultipartFile file;
    private Integer merchantId;
    private Integer factoryId;
    private Boolean override;

}

