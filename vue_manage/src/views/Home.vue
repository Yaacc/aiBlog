<template>
  <div>
    <div>
      <el-row :gutter="30">
        <el-col :span="6">
          <el-card>
            <div class="card-name">用户总数</div>
            <div class="card-content"><i class="el-icon-user mr5px"/>{{ numberOfUsers }}
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card>
            <div class="card-name">文章数量</div>
            <div class="card-content"><i class="el-icon-document mr5px"/>{{ numberOfDocument }}</div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card>
            <div class="card-name">文件数量</div>
            <div class="card-content"><i class="el-icon-folder-opened mr5px"/>{{ numberOfFiles }}</div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card>
            <div class="card-name">消息数量</div>
            <div class="card-content">
              <el-badge value="新" :hidden="haveNewMessage"><i class="el-icon-message mr5px"/>{{ numberOfMessage }}
              </el-badge>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <div>
      <el-row :gutter="24">
        <el-col :span="8">
          <el-card>
            <div id="lineOfUsers" style="width: 450px; height: 400px"></div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card>
            <div id="userPie" style="width: 450px; height: 400px"></div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card>
            <div >
              <el-calendar v-model="value"></el-calendar>
            </div>
          </el-card>

        </el-col>
      </el-row>
    </div>


  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "Home",
  data() {
    return {
      numberOfUsers: 0,       // 用户数量
      numberOfDocument: 0,    // 文章数量
      numberOfFiles: 0,       // 文件数量
      numberOfMessage: 0,     // 消息数量
      haveNewMessage: false,

    };
  },
  created() {
    this.load()
  },
  mounted() {
    // 基于准备好的dom，初始化echarts实例
    var pieChart = echarts.init(document.getElementById('userPie'));
    // 绘制图表
    pieChart.setOption({
      title: {
        text: '用户男女比例',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',   // 排列
        left: 'left'          // 对齐
      },
      series: [
        {
          // name: 'Access From',
          type: 'pie',
          radius: '70%',    // 大小比例
          data: [
            {value: 1048, name: '男性'},
            {value: 735, name: '女性'},
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    });

    var lineChart = echarts.init(document.getElementById('lineOfUsers'));
    lineChart.setOption({
      title: {
        text: '用户增长曲线',
        left: 'center',
      },
      // 触发显示数值
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          // shadow || line
          type: 'shadow'
        }
      },
      xAxis: {
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [150, 230, 224, 218, 260, 268, 294],
          type: 'line',
          // 显示数值
          // label: {
          //   show: true,
          //   position: 'bottom',
          //   // textStyle: {
          //   //   fontSize: 12
          //   // }
          // }
        }
      ]
    });

  },
  methods: {
    load() {
      // 更新用户数量
      this.request.get("/user/total").then(res => {
        console.log(res)
        this.numberOfUsers = res.data
      })
      // 更新文件数量
      this.request.get("/files/total").then(res => {
        console.log(res)
        this.numberOfFiles = res.data
      })
    },
  }
};
</script>
<style>
.el-row {
  margin-bottom: 40px;
}
.el-row :last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 10px;
}
.el-card{
  /*background-color: #d4d9e1;*/
}
.card-name {
  text-align: center;
  font-size: 18px;
  font-weight: bold;
}

.card-content {
  padding: 10px 0 0 0;
  text-align: center;
  font-size: 18px;
  font-weight: bold;
}
.ele-shadow{
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}
</style>
