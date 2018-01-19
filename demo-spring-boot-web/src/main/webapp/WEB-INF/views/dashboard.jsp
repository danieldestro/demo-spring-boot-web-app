<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<head>
  <!-- jvectormap -->
  <link rel="stylesheet" href="webjars/adminlte/2.3.11/plugins/jvectormap/jquery-jvectormap-1.2.2.css" />
    <!-- Morris charts -->
  <link rel="stylesheet" href="webjars/adminlte/2.3.11/plugins/morris/morris.css"/>
</head>

<section class="content-header">
  <h1 id="title">Dashboard</h1>
  <!--
  <ol class="breadcrumb">
    <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
    <li class="active">Here</li>
  </ol>
  -->
</section>

<section class="content">

<div class="row">
    <!-- Small boxes (Stat box) -->
    <div class="col-lg-3 col-xs-6">
      <!-- small box -->
      <div class="small-box bg-aqua">
        <div class="inner">
          <h3>150</h3>
          <p>Proposals Won (Qrtly)</p>
        </div>
        <div class="icon">
          <i class="ion ion-bag"></i>
        </div>
        <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
      </div>
    </div>
    <!-- ./col -->
    <div class="col-lg-3 col-xs-6">
      <!-- small box -->
      <div class="small-box bg-green">
        <div class="inner">
          <h3>20<sup style="font-size: 20px">%</sup></h3>
          <p>Proposal Requote Rate (Qrtly)</p>
        </div>
        <div class="icon">
          <i class="ion ion-stats-bars"></i>
        </div>
        <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
      </div>
    </div>
    <!-- ./col -->
    <div class="col-lg-3 col-xs-6">
      <!-- small box -->
      <div class="small-box bg-yellow">
        <div class="inner">
          <h3>44</h3>
          <p>Active Casper Users</p>
        </div>
        <div class="icon">
          <i class="ion ion-person-add"></i>
        </div>
        <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
      </div>
    </div>
    <!-- ./col -->
    <div class="col-lg-3 col-xs-6">
      <!-- small box -->
      <div class="small-box bg-red">
        <div class="inner">
          <h3>65</h3>
          <p>New Deals Created</p>
        </div>
        <div class="icon">
          <i class="ion ion-pie-graph"></i>
        </div>
        <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
      </div>
    </div>
    <!-- ./col -->
</div>
    
    
<div class="row">
    <div class="col-md-8">
        <!-- MAP & BOX PANE -->
        <div class="box box-success">
        <div class="box-header with-border">
          <h3 class="box-title">Proposals Won (Qrtly)</h3>
        
          <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
            </button>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
          </div>
        </div>
        <!-- /.box-header -->
        <div class="box-body no-padding">
          <div class="row">
            <div class="col-md-9 col-sm-8">
              <div class="pad">
                <!-- Map will be created here -->
                <div id="world-map-markers" style="height: 325px;"></div>
              </div>
            </div>
            <!-- /.col -->
            <div class="col-md-3 col-sm-4">
              <div class="pad box-pane-right bg-green" style="min-height: 280px">
                <div class="description-block margin-bottom">
                  <div class="sparkbar pad" data-color="#fff">90,70,90,70,75,80,70</div>
                  <h5 class="description-header">8390</h5>
                  <span class="description-text">Total Products Sold</span>
                </div>
                <!-- /.description-block -->
                <div class="description-block margin-bottom">
                  <div class="sparkbar pad" data-color="#fff">90,50,90,70,61,83,63</div>
                  <h5 class="description-header">30%</h5>
                  <span class="description-text">Operating Profit</span>
                </div>
                <!-- /.description-block -->
                <!-- <div class="description-block">
                  <div class="sparkbar pad" data-color="#fff">90,50,90,70,61,83,63</div>
                  <h5 class="description-header">70%</h5>
                  <span class="description-text">O</span>
                </div>  -->
                <!-- /.description-block -->
              </div>
            </div>
            <!-- /.col -->
          </div>
          <!-- /.row -->
        </div>
        <!-- /.box-body -->
        </div>
    </div>
    
    <div class="col-md-4">
          <div class="box box-default">
            <div class="box-header with-border">
              <h3 class="box-title">Regional Gross Margin (Qrtly)</h3>

              <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <div class="row">
                <div class="col-md-8">
                  <div class="chart-responsive">
                   <canvas id="pieChart" style="height:250px"></canvas>
                  </div>
                  <!-- ./chart-responsive -->
                </div>
                <!-- /.col -->
                <div class="col-md-4">
                  <ul class="chart-legend clearfix">
                    <li><i class="fa fa-circle-o text-red"></i> APJ</li>
                    <li><i class="fa fa-circle-o text-green"></i> AMS</li>
                    <li><i class="fa fa-circle-o text-light-blue"></i> EMEA</li>
                  </ul>
                </div>
                <!-- /.col -->
              </div>
              <!-- /.row -->
            </div>
            <!-- /.box-body -->
            <div class="box-footer no-padding">
              <ul class="nav nav-pills nav-stacked">
                <li><a href="#">AMS
                  <span class="pull-right text-red"><i class="fa fa-angle-down"></i> 12%</span></a></li>
                <li><a href="#">EMEA<span class="pull-right text-green"><i class="fa fa-angle-up"></i> 4%</span></a>
                </li>
                <li><a href="#">APJ
                  <span class="pull-right text-yellow"><i class="fa fa-angle-left"></i> 0%</span></a></li>
              </ul>
            </div>
            <!-- /.footer -->
          </div>
          <!-- /.box -->
    </div>
</div>

<div class="row">
    <div class="col-md-12">
      <div class="box">
        <div class="box-header with-border">
          <h3 class="box-title">Monthly Recap Report</h3>
    
          <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
            </button>
            <div class="btn-group">
              <button type="button" class="btn btn-box-tool dropdown-toggle" data-toggle="dropdown">
                <i class="fa fa-wrench"></i></button>
              <ul class="dropdown-menu" role="menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li class="divider"></li>
                <li><a href="#">Separated link</a></li>
              </ul>
            </div>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
          </div>
        </div>
        <!-- /.box-header -->
        <div class="box-body">
          <div class="row">
            <div class="col-md-8">
              <p class="text-center">
                <strong>Sales: Jan 1st 2017 - Mar 31st 2017</strong>
              </p>
    
              <div class="chart">
                <!-- Sales Chart Canvas -->
                <canvas id="salesChart" style="height: 180px;"></canvas>
              </div>
              <!-- /.chart-responsive -->
            </div>
            <!-- /.col -->
            <div class="col-md-4">
              <p class="text-center">
                <strong>Quarterly Target</strong>
              </p>
    
              <div class="progress-group">
                <span class="progress-text">Proposals Won</span>
                <span class="progress-number"><b>160</b>/200</span>
    
                <div class="progress sm">
                  <div class="progress-bar progress-bar-aqua" style="width: 80%"></div>
                </div>
              </div>
                            <!-- /.progress-group -->
              <div class="progress-group">
                <span class="progress-text">Net Revenue</span>
                <span class="progress-number"><b>600K</b>/1mil</span>
    
                <div class="progress sm">
                  <div class="progress-bar progress-bar-green" style="width: 60%"></div>
                </div>
              </div>

              <!-- /.progress-group -->
              <div class="progress-group">
                <span class="progress-text">Gross Margin Amount</span>
                <span class="progress-number"><b>250k</b>/500k</span>
    
                <div class="progress sm">
                  <div class="progress-bar progress-bar-yellow" style="width: 50%"></div>
                </div>
              </div>
              
              <!-- /.progress-group -->
              <div class="progress-group">
                <span class="progress-text">Operating Profit</span>
                <span class="progress-number"><b>200k</b>/600k</span>
    
                <div class="progress sm">
                  <div class="progress-bar progress-bar-red" style="width: 50%"></div>
                </div>
              </div>
            </div>
            <!-- /.col -->
          </div>
          <!-- /.row -->
        </div>
        <!-- ./box-body -->
        <div class="box-footer">
          <div class="row">
            <div class="col-sm-3 col-xs-6">
              <div class="description-block border-right">
                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 17%</span>
                <h5 class="description-header">$35,210.43</h5>
                <span class="description-text">TOTAL REVENUE</span>
              </div>
              <!-- /.description-block -->
            </div>
            <!-- /.col -->
            <div class="col-sm-3 col-xs-6">
              <div class="description-block border-right">
                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> 0%</span>
                <h5 class="description-header">$10,390.90</h5>
                <span class="description-text">TOTAL COST</span>
              </div>
              <!-- /.description-block -->
            </div>
            <!-- /.col -->
            <div class="col-sm-3 col-xs-6">
              <div class="description-block border-right">
                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 20%</span>
                <h5 class="description-header">$24,813.53</h5>
                <span class="description-text">Operating PROFIT</span>
              </div>
              <!-- /.description-block -->
            </div>
            <!-- /.col -->
            <div class="col-sm-3 col-xs-6">
              <div class="description-block">
                <span class="description-percentage text-black">50%</span>
                <h5 class="description-header"><b>1.05mil</b>/2.1mil</h5>
                <span class="description-text">Overall Targets (Qrtly)</span>
              </div>
              <!-- /.description-block -->
            </div>
          </div>
          <!-- /.row -->
        </div>
        <!-- /.box-footer -->
      </div>
      <!-- /.box -->
    </div>
    <!-- /.col -->
</div>
<!-- /.row -->

</section>
<!-- /.content -->
    
<!-- Sparkline -->
<script src="webjars/adminlte/2.3.11/plugins/sparkline/jquery.sparkline.min.js"></script>
<!-- jvectormap -->
<script src="webjars/adminlte/2.3.11/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
<script src="webjars/adminlte/2.3.11/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
<!-- SlimScroll 1.3.0 -->
<script src="webjars/adminlte/2.3.11/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- ChartJS 1.0.1 -->
<script src="webjars/adminlte/2.3.11/plugins/chartjs/Chart.min.js"></script>
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
<!--<script src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>-->
<script src="webjars/adminlte/2.3.11/plugins/morris/morris.min.js"></script>
<script src="webjars/adminlte/2.3.11/dist/js/pages/dashboard2.js"></script>
