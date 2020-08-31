interpret_approaches = ["Comprex"; "R200 & LR"; "R50 & LR"; "FW & LR"; "PW & LR"];
interpret_cost = [43.8 112.9 26.8 8.7 80.0];
interpret_mape = [3.2 2.9 4.5 7.9 4.7];

not_interpret_approaches = ["R50 & RF"; "R200 & RF"; "FW & RF"; "PW & RF"];
not_interpret_cost = [26.7 108.0 8.6 78];
not_interpret_mape = [0.8 0.3 8.7 4.0];

plot(interpret_cost, interpret_mape, '+', not_interpret_cost, not_interpret_mape, 'x', 'LineWidth', 2 ,'MarkerSize',16);

title('Apache Lucene', 'Fontsize',80);
xlabel('Cost (minutes)');
ylabel('Accuracy (MAPE)');

xlim([0.0, 150.0]);
ylim([0.0, 9.5]);

set(gca,'FontSize',20);

dx = 0.6; dy = 0.4; 
text(interpret_cost+dx, interpret_mape+dy, interpret_approaches, 'Fontsize', 20);
text(not_interpret_cost+dx, not_interpret_mape+dy, not_interpret_approaches, 'Fontsize', 20);

%legend('Interpretable','Not interpretable','Location','northwest');

dim = [.15 .2 .2 .2];
str = {'LR: Linear Regression'; 'RF: Random Forest'; 'R200: 200 random configurations'};
%annotation('textbox',dim,'String',str,'FitBoxToText','on','Fontsize', 16);

set(gcf,'Position',[100 100 550 400])

saveas(gcf,'IndexFiles.pdf');