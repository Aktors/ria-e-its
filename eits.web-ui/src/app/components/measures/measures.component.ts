import { Component, ViewChild } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import {MeasuresSearchComponent} from './measures-search/measures-search.component';
import {MeasuresTableComponent} from './measures-table/measures-table.component';
import {QueryCriterionDto} from '../../model/table.type';

@Component({
  selector: 'app-measures',
  templateUrl: './measures.component.html',
  styleUrl: './measures.component.css',
  standalone: true,
  imports: [MatTableModule, MatPaginatorModule, MatSortModule, MeasuresSearchComponent, MeasuresTableComponent]
})
export class MeasuresComponent {
  @ViewChild(MeasuresTableComponent) measuresTable!: MeasuresTableComponent;

  onSearch($event: QueryCriterionDto[]) {
    if (this.measuresTable) {
      this.measuresTable.setFilter($event);
    }
  }

  onVersionChange($event: string) {
    if (this.measuresTable) {
      this.measuresTable.setVersion($event);
    }
  }
}
