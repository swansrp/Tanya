/**
 * Title: TokenVO
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-4-6 20:51
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.common.vo
 */
package com.srct.service.tanya.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TokenVO {
    private String userName;
    private String Token;
}
