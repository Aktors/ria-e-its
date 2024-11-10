import { DataSource } from '@angular/cdk/collections';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import {map} from 'rxjs/operators';
import {Observable, merge, BehaviorSubject } from 'rxjs';
import {MeasureDto} from '../../model/measure.type';
import {MeasuresService} from '../../services/api/measures.service';
import {QueryCriterionDto} from '../../model/table.type';

export class MeasuresDatasource extends DataSource<MeasureDto> {
  filter$ = new BehaviorSubject<QueryCriterionDto[]>([]);
  sourceData$ = new BehaviorSubject<MeasureDto[]>([]);
  isLoading$ = new BehaviorSubject<boolean>(true);
  filteredDataCount: number = 0;

  paginator: MatPaginator | undefined;
  sort: MatSort | undefined;

  constructor(private measuresService : MeasuresService) {
    super();
  }

  connect(): Observable<MeasureDto[]> {
    if (this.paginator && this.sort) {
      return merge(this.sourceData$, this.paginator.page, this.sort.sortChange, this.filter$)
        .pipe(map(() => {
          const filteredData = this.getFilteredData([...this.sourceData$.value ]);
          this.filteredDataCount = filteredData.length;
          return this.getPagedData(this.getSortedData(filteredData));
        }));
    } else {
      throw Error('Please set the paginator and sort on the data source before connecting.');
    }
  }

  disconnect(): void {
    this.sourceData$.complete();
    this.filter$.complete();
  }

  updateSearchCriteria(criteria: QueryCriterionDto[]): void {
    this.filter$.next(criteria);
  }

  public loadData(version: string): void {
    this.sourceData$.next([]);
    this.isLoading$.next(true);
    this.measuresService.getByVersion(version)
      .subscribe({
        next: (data: MeasureDto[]) => {
          this.sourceData$.next(data);
          this.isLoading$.next(false);
        },
        error: (_) => {
          this.sourceData$.next([]);
          this.isLoading$.next(false);
        }
      });
  }

  private getPagedData(data: MeasureDto[]): MeasureDto[] {
    if (this.paginator) {
      const startIndex = this.paginator.pageIndex * this.paginator.pageSize;
      return data.splice(startIndex, this.paginator.pageSize);
    } else {
      return data;
    }
  }

  private getFilteredData(data: MeasureDto[]): MeasureDto[] {
    let filteredData = [...data];
    this.filter$.value.forEach((filter) => {
      filteredData = filteredData.filter(item =>{
        const fieldValue = item[filter.field as keyof MeasureDto];
        return typeof fieldValue === 'string' && fieldValue?.toLowerCase().includes(filter.value.toLowerCase());
      });
    })
    return filteredData;
  }

  private getSortedData(data: MeasureDto[]): MeasureDto[] {
    if (!this.sort || !this.sort.active || this.sort.direction === '') {
      return data;
    }

    return data.sort((a, b) => {
      const isAsc = this.sort?.direction === 'asc';
      switch (this.sort?.active) {
        case 'code': return compare(a.code, b.code, isAsc);
        case 'title': return compare(+a.title, +b.title, isAsc);
        default: return 0;
      }
    });
  }
}

function compare(a: string | number, b: string | number, isAsc: boolean): number {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
