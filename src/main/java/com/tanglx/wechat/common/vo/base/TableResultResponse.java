package com.tanglx.wechat.common.vo.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tanglx.wechat.common.util.ObjectUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.function.Supplier;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-05-25
 */
@Getter
@Setter
@ApiModel
public class TableResultResponse<T> extends BaseResponse {

    @ApiModelProperty("分页对象")
    TableData<T> data;

    public TableResultResponse(IPage page, Supplier<T> target) {
        this.data = new TableData<>(page, target);
    }

    public TableResultResponse(long total, List<T> rows) {
        this.data = new TableData<>(total, rows);
    }

    public TableResultResponse(long total, List<T> rows, int page, int limit) {
        this.data = new TableData<>(total, rows, page, limit);
    }

    public TableResultResponse() {
        this.data = new TableData<>();
    }

    TableResultResponse<T> total(int total) {
        this.data.setTotalElements(total);
        return this;
    }

    TableResultResponse<T> total(List<T> rows) {
        this.data.setContent(rows);
        return this;
    }

    @Data
    public class TableData<T> {
        @ApiModelProperty("总记录数")
        long totalElements;
        @ApiModelProperty("当前页中存放的数据")
        List<T> content;
        @ApiModelProperty("每页的记录数 默认10条")
        long pageSize;
        @ApiModelProperty("总页数")
        long totalPages;
        @ApiModelProperty("当前页")
        private int page;
        @ApiModelProperty("是否有上一页")
        private boolean hasPrevious = false;
        @ApiModelProperty("是否有下一页")
        private boolean hasNext = false;

        public TableData(IPage iPage, Supplier<T> target) {
            this.totalElements = iPage.getTotal();
            this.content = ObjectUtil.copyList(iPage.getRecords(), target);
            this.page = (int) iPage.getPages();
            this.pageSize = (int) iPage.getTotal();
            refresh();
        }

        public TableData(long total, List<T> rows) {
            this.totalElements = total;
            this.content = rows;
            refresh();
        }

        public TableData(long total, List<T> rows, int page, int limit) {
            this.totalElements = total;
            this.content = rows;
            this.pageSize = limit;
            this.totalPages = getTotalPageCount();
            this.page = page;
            refresh();
        }

        public TableData() {
        }

        /**
         * 取总页数
         */
        private final long getTotalPageCount() {
            if (totalElements % pageSize == 0) {
                return totalElements / pageSize;
            } else {
                return totalElements / pageSize + 1;
            }
        }

        /**
         * 刷新当前分页对象数据
         */
        private void refresh() {
            if (totalPages <= 1) {
                hasPrevious = false;
                hasNext = false;
            } else if (page == 0) {
                hasPrevious = false;
                hasNext = true;
            } else if (page == totalPages - 1) {
                hasPrevious = true;
                hasNext = false;
            } else {
                hasPrevious = true;
                hasNext = true;
            }
        }
    }
}
