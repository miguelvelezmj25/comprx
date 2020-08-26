interpret_approaches = ["Comprex"; "LR & R200"];
interpret_cost = [31.9 110.6];
interpret_mape = [2.9 93.9];

not_interpret_approaches = ["RF & R200"];
not_interpret_cost = [106.0];
not_interpret_mape = [0.7];

plot(interpret_cost, interpret_mape, '+', not_interpret_cost, not_interpret_mape, 'x', 'LineWidth', 2 ,'MarkerSize',16);

title('H2', 'Fontsize',80);
xlabel('Cost (m)');
ylabel('Accuracy (MAPE)');

xlim([28.0, 132.0]);
ylim([-3.0, 102.0]);

set(gca,'FontSize',20);

dx = 2.0; dy = 3.0; 
text(interpret_cost+dx, interpret_mape+dy, interpret_approaches, 'Fontsize', 16);
text(not_interpret_cost+dx, not_interpret_mape+dy, not_interpret_approaches, 'Fontsize', 16);

legend('Interpretable','Not interpretable','Location','northwest');

set(gcf,'Position',[100 100 550 400])

saveas(gcf,'RunBenchC.pdf');