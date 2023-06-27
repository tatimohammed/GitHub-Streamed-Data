import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Chart } from 'chart.js';
import { Data } from './dashboard';
import { DataService } from './dashboard.service';

@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class Dashboard implements OnInit {

  @ViewChild('barChart') barChart!: ElementRef;
  chart!: Chart;

  @ViewChild('barChart2') barChart2!: ElementRef;
  chart2!: Chart;

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.getData();
    setInterval(() => {
      this.getData();
    }, 10000);
  }

  data: Data = new Data();

  getData() {
    this.dataService.fetchData().subscribe((response: Data) => {
      this.data = response;
      const months = this.data.months;
      const sortedMonths = Object.keys(months).sort((a, b) => parseInt(a) - parseInt(b)).map(key => ({ month: key, value: months[key] })).slice(0, 6);
      delete this.data.langs["null"];
      const languagesCount = Object.entries(this.data.langs)
        .sort((a, b) => b[1] - a[1]) // Sort languages based on their values in descending order
        .slice(0, 20) // Take the top 20 languages
        .map(([language, count]) => count);

      const languages = Object.entries(this.data.langs)
        .sort((a, b) => b[1] - a[1]) // Sort languages based on their values in descending order
        .slice(0, 20) // Take the top 20 languages
        .map(([language, count]) => language);

      const canvas = this.barChart.nativeElement.getContext('2d');
      const canvas2 = this.barChart2.nativeElement.getContext('2d');

      // Destroy existing chart instance if it exists
      if (this.chart) {
        this.chart.destroy();
      }
      if (this.chart2) {
        this.chart2.destroy();
      }
      this.chart = new Chart(canvas, {
        type: 'bar',
        data: {
          labels: sortedMonths.map(month => month.month),
          datasets: [{
            label: 'Repositories Count',
            data: sortedMonths.map(month => month.value),
            backgroundColor: 'rgb(23, 2, 53, 0.5)',
            borderColor: 'rgb(23, 2, 53)',
            borderWidth: 1
          }]
        },
        options: {
          responsive: true
        }
      });
      this.chart2 = new Chart(canvas2, {
        type: 'bar',
        data: {
          labels: languages,
          datasets: [{
            label: 'Repositories Count',
            data: languagesCount,
            backgroundColor: 'rgb(23, 2, 53, 0.5)',
            borderColor: 'rgb(23, 2, 53)',
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
          indexAxis: 'y',
          maintainAspectRatio: false
        }
      });
    });
  }
}
