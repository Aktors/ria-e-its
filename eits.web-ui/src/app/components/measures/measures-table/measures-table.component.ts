import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell, MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow, MatRowDef, MatTable
} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort, MatSortHeader} from "@angular/material/sort";
import {MeasuresDatasource } from '../measures.datasource';
import {MeasuresService} from '../../../services/api/measures.service';
import {MeasureDto} from '../../../model/measure.type';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {TypeTranslatePipe} from '../../../pipes/type-translate.pipe';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {AsyncPipe, NgIf} from '@angular/common';
import {QueryCriterionDto} from '../../../model/table.type';

@Component({
  selector: 'app-measures-table',
  templateUrl: './measures-table.component.html',
  styleUrl: './measures-table.component.css',
  animations: [
    trigger('detailExpand', [
      state('collapsed,void', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
  standalone: true,
  imports: [
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderRow,
    MatHeaderRowDef,
    MatPaginator,
    MatRow,
    MatRowDef,
    MatSort,
    MatSortHeader,
    MatTable,
    MatHeaderCellDef,
    MatIcon,
    MatIconButton,
    TypeTranslatePipe,
    MatProgressSpinner,
    AsyncPipe,
    NgIf
  ]
})
export class MeasuresTableComponent implements OnInit, AfterViewInit {
  displayedColumnsWithExpand = ['type','code','title','expand'];
  dataSource!: MeasuresDatasource;
  expandedRow: MeasureDto | null = null;
  isLoading = true;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<MeasureDto>;

  constructor(private measuresService: MeasuresService) {
  }
  ngOnInit(): void {
    this.dataSource = new MeasuresDatasource(this.measuresService);
    this.dataSource.isLoading$.subscribe((isLoading) => {
      this.isLoading = isLoading;
    });
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
    this.isLoading = true;
  }

  setVersion(version: string): void {
    if (this.dataSource) {
      this.dataSource.loadData(version);
    }
  }

  setFilter(filter: QueryCriterionDto[]): void {
    this.dataSource.updateSearchCriteria(filter)
  }
}
